//
//  RootViewController.h
//  ProjectReality
//
//  Created by Jacob Hanshaw on 10/19/12.
//  Copyright (c) 2012 Jacob Hanshaw. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "HorizontalTableView.h"

#define CENTER_BUTTON_WIDTH 70
#define CENTER_BUTTON_HEIGHT 70

typedef enum {
    GAMES_TAB_INDEX,
    TARGETS_TAB_INDEX,
    EMPTY_TAB_INDEX,
    ARMORY_TAB_INDEX,
    SETTINGS_TAB_INDEX,
    NUM_OF_INDEXES
} TabBarIndex;

@interface RootTabBarController : UITabBarController<UINavigationControllerDelegate, UIImagePickerControllerDelegate,UITableViewDataSource, UITableViewDelegate> {
    HorizontalTableView *targetTableView;
    UIImagePickerController *picker;
    
    int                      machineGunBulletsLeft;
    NSMutableArray          *shots;
    CGFloat                  zoom;
}

@property (readwrite) CGFloat zoom;
@property (readwrite) int machineGunBulletsLeft;
@property (nonatomic) NSMutableArray *shots;

- (void)takePicture;
+ (RootTabBarController *)sharedRootViewController;

@end
