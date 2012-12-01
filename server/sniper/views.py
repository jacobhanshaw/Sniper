import json;
from django.http import HttpResponseRedirect, HttpResponse;
from django.shortcuts import render_to_response;
from django.contrib.auth.models import User;
from django.db import IntegrityError;
from sniper.models import Group, Player;


def jsonresponse(d):
	return HttpResponse(json.dumps(d), 'application/json');

def home(request):
	return render_to_response('testing.html');

def login(request):
	return jsonresponse(None);

def logout(request):
	return jsonresponse(None);
	
def register(request):
	try:
		user = User.objects.create_user('john', 'lennon@thebeatles.com', 'johnpassword');
		user.save();
		return jsonresponse({'success': True});
		
	except IntegrityError:
		# Username or email has already been taken
		return jsonresponse({'error': 'Already taken'});

def groups(request):
	objs = [{
		'id': x.id,
		'name': x.name,
	} for x in Group.objects.all()];
	return jsonresponse(objs);

def groups_create(request):
	return jsonresponse({'success': True});
