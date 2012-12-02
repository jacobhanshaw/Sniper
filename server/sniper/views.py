import json;
import os.path;
import random;
from datetime import datetime;
from django.http import HttpResponseRedirect, HttpResponse;
from django.shortcuts import render_to_response;
from django.contrib.auth.models import User;
from django.contrib import auth;
from django.db.models import Count;
from django.db import IntegrityError;
from sniper.settings import MEDIA_ROOT;
from sniper.models import Group, Player, Mission, Killshot;


def handleimage(f, newname):
	if not f:
		return None;

	result = os.path.join(MEDIA_ROOT, newname);
	with open(result, 'wb') as destination:
		for chunk in f.chunks():
			destination.write(chunk);
			
	return result;
	
def jsonresponse(d):
	return HttpResponse(json.dumps(d), 'application/json');

def home(request):
	return render_to_response('testing.html');

def login(request):
	username = request.POST.get('username', '');
	password = request.POST.get('password', '');

	user = auth.authenticate(username=username, password=password);
	if user is not None:
		if user.is_active:
			auth.login(request, user);
			return jsonresponse({'success': True});
		else:
			# Return a 'disabled account' error message
			pass;
	else:
		# Return an 'invalid login' error message.
		pass;
		
	return jsonresponse({'error': 'Could not log user in'});

def logout(request):
	auth.logout(request);
	return jsonresponse({'success': True});
	
def register(request):
	username = request.POST.get('username', '');
	firstname = request.POST.get('firstname', '');
	lastname = request.POST.get('lastname', '');
	password = request.POST.get('password', '');
	
	try:
		user = User.objects.create_user(username, 'lennon@thebeatles.com', password);
		user.first_name = firstname;
		user.last_name = lastname;
		user.save();
		return jsonresponse({'id': user.id, 'success': True});
		
	except IntegrityError:
		# Username or email has already been taken
		return jsonresponse({'error': 'Already taken'});

def testimg(request):
	print(request.FILES);
	f = request.FILES.get('image', None);
	print(handleimage(f, 'img_%d.jpg' % request.user.id));
	return jsonresponse({'success': False});

def groups(request):
	# Fetch only groups that haven't started
	groups = Group.objects.filter(is_started=False);

	# Determine how many players are in each group
	groups = groups.annotate(num_players=Count('player'));

	objs = [{
		'id': x.id,
		'name': x.name,
		'players': x.num_players,
	} for x in groups];
	return jsonresponse(objs);

def groups_create(request):
	name = request.POST.get('name', '').strip();
	if len(name) < 2:
		return jsonresponse({'error': 'Name is not specified or too short'});

	group = Group();
	group.name = name;
	group.is_started = False;
	group.started_time = datetime.now();
	group.save();

	# TODO Also add the current user to this group
	
	return jsonresponse({'success': True, 'group': group.id});

def groups_join(request):
	groupid = request.POST.get('id', 0);
	if groupid == 0:
		return jsonresponse({'error': 'No group with that id'});
		
	group = Group.objects.get(id=groupid);

	possible = Player.objects.filter(user=request.user, group=group);
	if len(possible) > 0:
		return jsonresponse({'error': 'User is already in the group'});

	if group.is_started:
		return jsonresponse({'error': 'Game has already started'});

	player = Player();
	player.user = request.user;
	player.group = group;
	player.is_dead = False;
	player.save();
	return jsonresponse({'success': True, 'name': group.name});

def groups_start(request):
	groupid = request.POST.get('id', 0);
	if groupid == 0:
		return jsonresponse({'error': 'No group with that id'});

	group = Group.objects.get(id=groupid);

	possible = Player.objects.filter(user=request.user, group=group);
	if len(possible) < 1:
		return jsonresponse({'error': 'User not a member of the group'});

	if group.is_started:
		return jsonresponse({'error': 'Game has already started'});

	group.is_started = True;
	group.save();
	return jsonresponse({'success': True});

def mission(request):
	groupid = request.GET.get('id', 0);
	if groupid == 0:
		return jsonresponse({'error': 'No group with that id'});

	group = Group.objects.get(id=groupid);
	if not group.is_started:
		return jsonresponse({'started': False});

	result = {'started': True};
	player = Player.objects.get(user=request.user, group=group);
	result['is_dead'] = player.is_dead;

	alive = Player.objects.filter(group=group, is_dead=False).count();
	result['alive_count'] = alive;

	if not player.is_dead:
		missions = Mission.objects.filter(player=player);
		if len(missions) > 0:
			mission = missions[0];
		else:
			mission = None;

		if (not mission) or (mission and mission.must_kill_player.is_dead):
			if not mission:
				mission = Mission();
				mission.player = player;

			# Determine the list of people who are playing the game and
			# still alive
			possible = Player.objects.filter(group=group, is_dead=False);
			possible_not_me = list();

			for x in possible:
				if x.user == request.user:
					print('found me');
				else:
					possible_not_me.append(x);

			# New mission
			if len(possible_not_me) > 0:
				mission.must_kill_player = random.choice(possible_not_me);
				mission.save();
			else:
				mission = None;

		d = {};
		if mission:
			p = mission.must_kill_player;
			d['id'] = p.user.id;
			d['name'] = p.user.first_name + ' ' + p.user.last_name;

		result['mission'] = d;
		
		# TODO Are the any killshots for me?
		
	return jsonresponse(result);
	
def killshot(request):
	groupid = request.POST.get('id', 0);
	if groupid == 0:
		return jsonresponse({'error': 'No group with that id'});

	group = Group.objects.get(id=groupid);
	if not group.is_started:
		return jsonresponse({'started': False});

	player = Player.objects.get(user=request.user, group=group);

	killshot = Killshot();
	killshot.user = request.user;
	killshot.group = group;
	killshot.killplayer = -1;
	killshot.save();

	f = request.FILES.get('image', None);
	loc = handleimage(f, 'killshot_%d.jpg' % killshot.id);
	
	# TODO Determine who the user is

	return jsonresponse({'success': True});
