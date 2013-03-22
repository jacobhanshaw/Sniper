//
//  Target.h
//  Sniper
//
//  Created by Jacob Hanshaw on 3/14/13.
//  Copyright (c) 2013 Jacob Hanshaw. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Target : NSObject {
    int           targetId;
    UIImageView  *imageView;
    NSString     *name;
}

@property(readwrite)     int           targetId;
@property(nonatomic)     UIImageView  *imageView;
@property(nonatomic)     NSString     *name;

@end
