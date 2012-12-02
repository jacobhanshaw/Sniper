from django.contrib.auth.models import User;
from django.db.models.signals import post_save;
from django.db import models;

# In case we need more user fields, the following code will give us a
# user profile.
"""
class UserProfile(models.Model):  
	user = models.OneToOneField(User);

	def __str__(self):  
		return "%s's profile" % self.user;


def create_user_profile(sender, instance, created, **kwargs):  
	if created:  
		profile, created = UserProfile.objects.get_or_create(user=instance);

post_save.connect(create_user_profile, sender=User);
"""

class Group(models.Model):
	name = models.CharField(max_length=100, blank=False);
	is_started = models.BooleanField();
	started_time = models.DateTimeField();

	def __str__(self):
		return '%s (started: %s)' % (self.name, str(self.is_started));

class Player(models.Model):
	user = models.ForeignKey(User);
	group = models.ForeignKey(Group);
	is_dead = models.BooleanField();
	#kills = models.IntegerField();

	def __str__(self):
		return '%s -- %s' % (self.user.username, self.group.name);

class Mission(models.Model):
	player = models.ForeignKey(Player, related_name='player');
	must_kill_player = models.ForeignKey(Player, related_name='must_kill_player');

	def __str__(self):
		return '%s to kill %s' % (self.player.user.username, self.must_kill_player.user.username);

class Killshot(models.Model):
	user = models.ForeignKey(User);
	group = models.ForeignKey(Group);
	time = models.DateTimeField(auto_now_add=True);
	killplayer = models.IntegerField();
