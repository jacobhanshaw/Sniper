//
//  AppModel.h
//  Sniper
//
//  Created by Jacob Hanshaw on 10/26/12.
//  Copyright (c) 2012 Jacob Hanshaw. All rights reserved.
//

#pragma once

#import <Foundation/Foundation.h>

@interface AppModel : NSObject {
    NSUserDefaults *defaults;
    NSString       *name;
    int            userId;
    NSString       *password;
    
    int             machineGunRounds;
    NSMutableArray *photoObjects;
}

@property (nonatomic) NSString *name;
@property (readwrite) int userId;
@property (nonatomic) NSString *password;

@property (readwrite) int machineGunRounds;
@property (nonatomic) NSMutableArray *photoObjects;

+ (AppModel *)sharedAppModel;

-(void)initUserDefaults;
-(void)saveUserDefaults;
-(void)loadUserDefaults;
-(void)clearUserDefaults;

@end
