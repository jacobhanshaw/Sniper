//
//  AppModel.h
//  Sniper
//
//  Created by Jacob Hanshaw on 10/26/12.
//  Copyright (c) 2012 Jacob Hanshaw. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface AppModel : NSObject {
    NSUserDefaults *defaults;
}



+ (AppModel *)sharedAppModel;

-(void)initUserDefaults;
-(void)saveUserDefaults;
-(void)loadUserDefaults;
-(void)clearUserDefaults;

@end
