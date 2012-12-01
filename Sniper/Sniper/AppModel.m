//
//  AppModel.m
//  Ninja
//
//  Created by Jacob Hanshaw on 10/26/12.
//  Copyright (c) 2012 Jacob Hanshaw. All rights reserved.
//

#import "AppModel.h"

@implementation AppModel

+ (id)sharedAppModel
{
    static dispatch_once_t pred = 0;
    __strong static id _sharedObject = nil;
    dispatch_once(&pred, ^{
        _sharedObject = [[self alloc] init]; // or some other init method
    });
    return _sharedObject;
}


#pragma mark User Defaults

-(void)initUserDefaults{
    defaults = [NSUserDefaults standardUserDefaults];
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
