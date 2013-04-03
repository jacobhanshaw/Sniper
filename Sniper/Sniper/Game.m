//
//  Game.m
//  Sniper
//
//  Created by Jacob Hanshaw on 3/14/13.
//  Copyright (c) 2013 Jacob Hanshaw. All rights reserved.
//

#import "Game.h"

@implementation Game

@synthesize name, type, gameId, currentTargets, allTargets;


NSString *stringWithGameType(GameType input) {
    NSArray *arr = @[
                     @"Assassins",           
                     @"Error"
                     ];
    return (NSString *)[arr objectAtIndex:input];
}

@end
