//
//  AppModel.h
//  Sniper
//
//  Created by Jacob Hanshaw on 10/26/12.
//  Copyright (c) 2012 Jacob Hanshaw. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Game.h"
#import "Player.h"

#define COREPAGE   "http://www.jacobhanshaw.com"
#define MEDIAEXT   "/media/"
#define MISSIONEXT "/mission/"
#define GAMESEXT   "/games/"
#define GAMEEXT    "/game/"
#define PLAYEREXT  "/player/"
#define TARGETEXT  "/target/"

@interface AppModel : NSObject {
    
    NSUserDefaults *defaults;
    
    int             machineGunRounds;
    NSMutableArray *photoObjects;
    NSMutableArray *targets;
    
    Game *currentGame;
    Player *user;
    
}

@property (readwrite) int machineGunRounds;
@property (nonatomic) NSMutableArray *photoObjects;
@property (nonatomic) NSMutableArray *targets;

@property (nonatomic) Game *currentGame;
@property (nonatomic) Player *user;

+ (AppModel *)sharedAppModel;

-(void)initUserDefaults;
-(void)saveUserDefaults;
-(void)loadUserDefaults;
-(void)clearUserDefaults;

@end
