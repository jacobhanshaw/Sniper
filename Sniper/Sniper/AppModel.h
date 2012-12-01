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
    int             machineGunRounds;
    NSMutableArray *shots;
}

@property (readwrite) int machineGunRounds;
@property (nonatomic) NSMutableArray *shots;

+ (AppModel *)sharedAppModel;

-(void)initUserDefaults;
-(void)saveUserDefaults;
-(void)loadUserDefaults;
-(void)clearUserDefaults;

@end
