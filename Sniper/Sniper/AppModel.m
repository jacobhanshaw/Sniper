//
//  AppModel.m
//  Ninja
//
//  Created by Jacob Hanshaw on 10/26/12.
//  Copyright (c) 2012 Jacob Hanshaw. All rights reserved.
//

#import "AppModel.h"

@implementation AppModel

@synthesize name, userId, password, machineGunRounds, photoObjects, targetName, targetImageLocation, missionNumber, targetsLeft;

+ (id)sharedAppModel
{
    static dispatch_once_t pred = 0;
    __strong static id _sharedObject = nil;
    dispatch_once(&pred, ^{
        _sharedObject = [[self alloc] init]; // or some other init method
    });
    return _sharedObject;
}

#pragma mark Init/dealloc
-(id)init {
    self = [super init];
    if (self) {
		//Init USerDefaults
		defaults = [NSUserDefaults standardUserDefaults];
        photoObjects = [[NSMutableArray alloc] init];
        machineGunRounds = 0;
      //  motionManager = [[CMMotionManager alloc] init];
        NSNotificationCenter *dispatcher = [NSNotificationCenter defaultCenter];

	}
    return self;
}


#pragma mark User Defaults

-(void)initUserDefaults{
    defaults = [NSUserDefaults standardUserDefaults];
    machineGunRounds = 0;
}

-(void)saveUserDefaults {
	NSLog(@"Model: Saving User Defaults");
	
    
	[defaults synchronize];
}

-(void)loadUserDefaults {
	NSLog(@"Model: Loading User Defaults");
    if(defaults == nil) [self initUserDefaults];
	[defaults synchronize];
    
    
}

-(void)clearUserDefaults {
	NSLog(@"Model: Clearing User Defaults");

    
	[defaults synchronize];
}

@end
