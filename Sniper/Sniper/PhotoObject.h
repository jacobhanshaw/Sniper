//
//  PhotoObject.h
//  Sniper
//
//  Created by Jacob Hanshaw on 12/1/12.
//  Copyright (c) 2012 Jacob Hanshaw. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface PhotoObject : NSObject {
    UIImage *image;
    NSData *photoData;
    NSString *mediaFileName;
    NSURL   *imageURL;
}

@property (nonatomic) UIImage *image;
@property (nonatomic) NSData *photoData;
@property (nonatomic) NSString *mediaFileName;
@property (nonatomic) NSURL *imageURL;

@end
