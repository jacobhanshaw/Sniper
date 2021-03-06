//
//  AppDelegate.h
//  Sniper
//
//  Created by Jacob Hanshaw on 11/29/12.
//  Copyright (c) 2012 Jacob Hanshaw. All rights reserved.
//

#pragma once

#import <UIKit/UIKit.h>
#import <CoreData/CoreData.h>
#import "AppModel.h"

@class RootTabBarController;

@interface AppDelegate : UIResponder <UIApplicationDelegate>

@property (strong, nonatomic) UIWindow *window;

@property (strong, nonatomic) RootTabBarController *viewController;

@property (readonly, strong, nonatomic) NSManagedObjectContext *managedObjectContext;
@property (readonly, strong, nonatomic) NSManagedObjectModel *managedObjectModel;
@property (readonly, strong, nonatomic) NSPersistentStoreCoordinator *persistentStoreCoordinator;

- (void)saveContext;
- (NSURL *)applicationDocumentsDirectory;

@end
