//
//  AppModel.h
//  Sniper
//
//  Created by Jacob Hanshaw on 10/26/12.
//  Copyright (c) 2012 Jacob Hanshaw. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Game.h"

@interface AppModel : NSObject {
    
    NSUserDefaults *defaults;
    
    int             machineGunRounds;
    NSMutableArray *photoObjects;
    NSMutableArray *targets;
    
    Game *currentGame;
    
}

@property (readwrite) int machineGunRounds;
@property (nonatomic) NSMutableArray *photoObjects;
@property (nonatomic) NSMutableArray *targets;

@property (nonatomic) Game *currentGame;

+ (AppModel *)sharedAppModel;

-(void)initUserDefaults;
-(void)saveUserDefaults;
-(void)loadUserDefaults;
-(void)clearUserDefaults;

@end
