//
//  Player.h
//  Sniper
//
//  Created by Jacob Hanshaw on 3/15/13.
//  Copyright (c) 2013 Jacob Hanshaw. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Player : NSObject {
    UIImage        *image;
    int             userId;
    NSString       *name;
    NSString       *password;
    int             totalKills;
}

@property (nonatomic)    UIImage  *image;
@property (nonatomic)    NSString *name;
@property (readwrite)    int userId;
@property (nonatomic)    NSString *password;
@property (readwrite)    int       totalKills;

@end
