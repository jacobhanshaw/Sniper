//
//  GamesViewController.h
//  Sniper
//
//  Created by Jacob Hanshaw on 3/13/13.
//  Copyright (c) 2013 Jacob Hanshaw. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "HorizontalTableView.h"
#import "TargetScrollView.h"

@interface GamesViewController : UIViewController <UITableViewDelegate, UITableViewDataSource>
{
   // __weak IBOutlet UITableView *gamesTableView;
    HorizontalTableView *gamesTableView;
    TargetScrollView *gamesScrollView;
    NSMutableArray *reusableCells;
    
}

@property(nonatomic) NSMutableArray *reusableCells;

@end
