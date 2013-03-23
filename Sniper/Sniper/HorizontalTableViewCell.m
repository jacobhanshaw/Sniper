//
//  HorizontalTableViewCell.m
//  Sniper
//
//  Created by Jacob Hanshaw on 3/19/13.
//  Copyright (c) 2013 Jacob Hanshaw. All rights reserved.
//

#import "HorizontalTableViewCell.h"
#import "TargetCell.h"

@implementation HorizontalTableViewCell

- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        // Initialization code
    }
    return self;
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

- (UITableViewCell *)tableView:(UITableView *)_tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"TargetCell";
    
    TargetCell *cell = (TargetCell *)[_tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    
    if (cell == nil)
    {
        cell = [[TargetCell alloc] initWithFrame:CGRectMake(0, 0, self.frame.size.width, self.frame.size.height)];
    }
    
    cell.targetImageView = ((Target *)[data objectAtIndex:indexPath.row]).imageView;
    cell.targetNameLabel.text = ((Target *)[data objectAtIndex:indexPath.row]).name;
    
    return cell;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return 2; //[data count];
}

- (id)initWithFrame:(CGRect)frame
{
    if ((self = [super initWithFrame:frame]))
    {
        tableView = [[UITableView alloc] initWithFrame:CGRectMake(0, 0, 100, 320)];
        tableView.showsVerticalScrollIndicator = NO;
        tableView.showsHorizontalScrollIndicator = NO;
        tableView.transform = CGAffineTransformMakeRotation(-M_PI * 0.5);
        [tableView setFrame:CGRectMake(0, 0, 320, 100)];
        
        tableView.rowHeight = 100;
        tableView.backgroundColor =[UIColor clearColor];
        
        tableView.separatorStyle = UITableViewCellSeparatorStyleSingleLine;
        tableView.separatorColor = [UIColor clearColor];
        
        tableView.dataSource = self;
        tableView.delegate = self;
        [self addSubview:tableView];
    }
    
    return self;
}


@end
