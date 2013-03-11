//
//  HorizontalTableView.m
//  Sniper
//
//  Created by Jacob Hanshaw on 3/11/13.
//  Copyright (c) 2013 Jacob Hanshaw. All rights reserved.
//

#import "HorizontalTableView.h"

@implementation HorizontalTableView

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        // Initialization code
        self.showsVerticalScrollIndicator = NO;
        self.showsHorizontalScrollIndicator = NO;
        self.transform = CGAffineTransformMakeRotation(-M_PI * 0.5);
        [self setFrame:CGRectMake(frame.origin.x, frame.origin.y, frame.size.width, frame.size.height)];
        
        self.rowHeight = 100;
        self.backgroundColor = [UIColor clearColor];
        
        self.bounces = NO;
        self.separatorStyle = UITableViewCellSeparatorStyleSingleLine;
        self.separatorColor = [UIColor clearColor];
    }
    return self;
}

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect
{
    // Drawing code
}
*/

@end
