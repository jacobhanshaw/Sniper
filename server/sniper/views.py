import json;
import os.path;
from datetime import datetime;
from django.http import HttpResponseRedirect, HttpResponse;
from django.shortcuts import render_to_response;
from django.contrib.auth.models import User;
from django.contrib import auth;
from django.db import IntegrityError;
from sniper.settings import MEDIA_ROOT;
from sniper.models import Group, Player;


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
	# TODO Finish this
	
	try:
		user = User.objects.create_user('john', 'lennon@thebeatles.com', 'johnpassword');
		user.save();
		return jsonresponse({'success': True});
		
	except IntegrityError:
		# Username or email has already been taken
		return jsonresponse({'error': 'Already taken'});

def testimg(request):
	print(request.FILES);
	f = request.FILES.get('image', None);
	print(handleimage(f, 'img_%d.jpg' % request.user.id));
	return jsonresponse({'success': False});

def groups(request):
	objs = [{
		'id': x.id,
		'name': x.name,
	} for x in Group.objects.filter(is_started=False)];
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

	if not player.is_dead:
		# TODO Find mission object, etc.
		pass;
		
	return jsonresponse(result);
	
