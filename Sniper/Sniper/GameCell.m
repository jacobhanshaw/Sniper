//
//  GameCell.m
//  Sniper
//
//  Created by Jacob Hanshaw on 3/29/13.
//  Copyright (c) 2013 Jacob Hanshaw. All rights reserved.
//

#import "GameCell.h"

@implementation GameCell

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

@end
