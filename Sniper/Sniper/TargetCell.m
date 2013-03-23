//
//  HorizontalTableViewCell.m
//  Sniper
//
//  Created by Jacob Hanshaw on 3/13/13.
//  Copyright (c) 2013 Jacob Hanshaw. All rights reserved.
//

#import "TargetCell.h"

@implementation TargetCell

@synthesize targetImageView, targetNameLabel;

- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        // Initialization code
        targetImageView = [[UIImageView alloc] initWithFrame:self.frame];
        targetNameLabel = [[UILabel     alloc] initWithFrame:CGRectMake(0, self.frame.size.height-LABELHEIGHT, self.frame.size.width, self.frame.size.height)];
        
        targetNameLabel.textAlignment   =  UITextAlignmentCenter;
        targetNameLabel.textColor       = [UIColor redColor];
        targetNameLabel.backgroundColor = [UIColor clearColor];
        
        [self addSubview:targetNameLabel];
        [self addSubview:targetImageView];
        
        self.transform = CGAffineTransformMakeRotation(M_PI * 0.5);
    }
    return self;
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
