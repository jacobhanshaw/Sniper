//
//  HorizontalTableView.m
//  Sniper
//
//  Created by Jacob Hanshaw on 3/11/13.
//  Copyright (c) 2013 Jacob Hanshaw. All rights reserved.
//

#import "HorizontalTableView.h"
#import "TargetCell.h"
#import "Target.h"

@implementation HorizontalTableView

@synthesize data;

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        // Initialization code
        self.showsVerticalScrollIndicator = NO;
        self.showsHorizontalScrollIndicator = NO;
        self.transform = CGAffineTransformMakeRotation(-M_PI * 0.5);
        [self setFrame:CGRectMake(frame.origin.x, frame.origin.y, frame.size.width, frame.size.height)];
        
        self.rowHeight = ROWHEIGHT;
        self.backgroundColor = [UIColor clearColor];
        
        self.bounces = NO;
        self.separatorStyle = UITableViewCellSeparatorStyleSingleLine;
        self.separatorColor = [UIColor clearColor];
        self.dataSource = self;
    }
    return self;
}

#pragma mark TableView DataSource Methods

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"TargetCell";
    
    TargetCell *cell = (TargetCell *)[tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    
    if (cell == nil)
    {
        cell = [[TargetCell alloc] initWithFrame:CGRectMake(0, 0, self.frame.size.width, self.frame.size.height)];
    }
    
    [cell.targetImageView setImage:((Target *)[data objectAtIndex:indexPath.row]).image];
    cell.targetNameLabel.text = ((Target *)[data objectAtIndex:indexPath.row]).name;
    
    return cell;
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
