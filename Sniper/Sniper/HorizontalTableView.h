//
//  HorizontalTableView.h
//  Sniper
//
//  Created by Jacob Hanshaw on 3/11/13.
//  Copyright (c) 2013 Jacob Hanshaw. All rights reserved.
//

#import <UIKit/UIKit.h>

#define ROWHEIGHT 100

@interface HorizontalTableView : UITableView <UITableViewDataSource, UITableViewDelegate> {
    NSMutableArray *data;
}

@property (nonatomic) NSMutableArray *data;

@end
