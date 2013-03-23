//
//  HorizontalTableViewCell.m
//  Sniper
//
//  Created by Jacob Hanshaw on 3/13/13.
//  Copyright (c) 2013 Jacob Hanshaw. All rights reserved.
//

#import "TargetCell.h"
#import "ControlVariables.h"

@implementation TargetCell

@synthesize targetImageView, targetNameLabel;

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        // Initialization code
        targetImageView = [[UIImageView alloc] initWithFrame:CGRectMake(kTargetCellHorizontalInnerPadding, kTargetCellVerticalInnerPadding, kCellWidth - kTargetCellHorizontalInnerPadding * 2, kCellHeight - kTargetCellVerticalInnerPadding * 2)];
        targetImageView.opaque = YES;
        [self.contentView addSubview:targetImageView];
        
        
        targetNameLabel = [[UILabel     alloc] initWithFrame:CGRectMake(0, targetImageView.frame.size.height * 0.632, targetImageView.frame.size.width, targetImageView.frame.size.height * 0.37)];
        
        targetImageView.contentMode = UIViewContentModeScaleAspectFit;
        
        targetNameLabel.textAlignment   =  UITextAlignmentCenter;
        targetNameLabel.textColor       =  kTargetCellTextColor;
        targetNameLabel.backgroundColor =  [UIColor clearColor];
        
        [targetImageView addSubview:targetNameLabel];
        
        self.backgroundColor = kTargetCellBackgroundColor;
        
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
