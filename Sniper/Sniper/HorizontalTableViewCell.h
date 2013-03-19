//
//  HorizontalTableViewCell.h
//  Sniper
//
//  Created by Jacob Hanshaw on 3/19/13.
//  Copyright (c) 2013 Jacob Hanshaw. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface HorizontalTableViewCell : UITableViewCell <UITableViewDataSource, UITableViewDelegate> {
        UITableView *tableView;
        NSMutableArray *data;
}

@end
