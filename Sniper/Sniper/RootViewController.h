//
//  RootViewController.h
//  ProjectReality
//
//  Created by Jacob Hanshaw on 10/19/12.
//  Copyright (c) 2012 Jacob Hanshaw. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "HorizontalTableView.h"

typedef enum {
    GAMESINDEX,
    TARGETSINDEX,
    EMPTYINDEX,
    ARMORYINDEX,
    SETTINGSINDEX,
    NUMOFINDEXES
} TabBarIndex;

@interface RootViewController : UITabBarController<UINavigationControllerDelegate, UIImagePickerControllerDelegate,UITableViewDataSource, UITableViewDelegate> {
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
+ (RootViewController *)sharedRootViewController;

@end
