//
//  HorizontalTableViewCell.h
//  Sniper
//
//  Created by Jacob Hanshaw on 3/13/13.
//  Copyright (c) 2013 Jacob Hanshaw. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Target.h"

@interface TargetCell : UITableViewCell {
    UIImageView * targetImageView;
    UILabel *     targetNameLabel;
}

@property(nonatomic)    UIImageView * targetImageView;
@property(nonatomic)    UILabel *     targetNameLabel;

@end
