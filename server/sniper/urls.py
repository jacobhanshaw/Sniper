from django.conf.urls.defaults import patterns, include, url
from django.contrib.staticfiles.urls import staticfiles_urlpatterns

from django.contrib import admin
admin.autodiscover()

urlpatterns = patterns('',
    url(r'^testing$', 'sniper.views.home', name='home'),
    
    url(r'^login$', 'sniper.views.login', name='login'),
    url(r'^logout$', 'sniper.views.logout', name='logout'),
    url(r'^register$', 'sniper.views.register', name='register'),
    
    url(r'^groups$', 'sniper.views.groups', name='groups'),

	# Administation
    url(r'^admin/', include(admin.site.urls)),
)

urlpatterns += staticfiles_urlpatterns()

