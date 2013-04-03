//
//  ExtraTagButton.h
//  Sniper
//
//  Created by Jacob Hanshaw on 4/3/13.
//  Copyright (c) 2013 Jacob Hanshaw. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ExtraTagButton : UIButton {
    int section;
    int row;
}

@property (readwrite) int section;
@property (readwrite) int row;

@end
