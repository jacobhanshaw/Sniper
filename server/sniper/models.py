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

class Player(models.Model):
	user = models.ForeignKey(User);
	group = models.ForeignKey(Group);
	is_dead = models.BooleanField();
	must_kill_player = models.ForeignKey('self');
	#kills = models.IntegerField();

#class KillShots(models.Model):
#	user = models.ForeignKey(User);
#	group = models.ForeignKey(Group);
