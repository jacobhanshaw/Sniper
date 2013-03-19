//
//  Game.h
//  Sniper
//
//  Created by Jacob Hanshaw on 3/14/13.
//  Copyright (c) 2013 Jacob Hanshaw. All rights reserved.
//

#import <Foundation/Foundation.h>

typedef enum {
    ASSASSINS,
    NUMOFGAMETYPES
} GameType;

@interface Game : NSObject {
    
    GameType        type;
    int             gameId;
    NSMutableArray *currentTargets;
    NSMutableArray *allTargets;

}

@property (readwrite)     GameType        type;
@property (readwrite)     int             gameId;
@property (nonatomic)     NSMutableArray *currentTargets;
@property (nonatomic)     NSMutableArray *allTargets;

@end
