//
//  TargetScrollView.h
//  Sniper
//
//  Created by Jacob Hanshaw on 3/23/13.
//  Copyright (c) 2013 Jacob Hanshaw. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface TargetScrollView : UIScrollView <UIScrollViewDelegate>{
    NSMutableArray *data;
    BOOL           interactable;
}

@property (nonatomic) NSMutableArray *data;

- (void)loadPageScroller:(NSMutableArray *)targets interactable:(BOOL) inputInteractable target:(id) caller section:(int) section row:(int) row;

@end
