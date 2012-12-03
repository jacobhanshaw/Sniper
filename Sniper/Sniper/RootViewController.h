//
//  RootViewController.h
//  ProjectReality
//
//  Created by Jacob Hanshaw on 10/19/12.
//  Copyright (c) 2012 Jacob Hanshaw. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "AppModel.h"
#import "MissionViewController.h"
#import "AFJSONRequestOperation.h"
#import "LogInViewController.h"

@interface RootViewController : UIViewController {
    
}

+ (RootViewController *)sharedRootViewController;

@end
