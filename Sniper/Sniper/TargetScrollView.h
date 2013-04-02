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
}

@property (nonatomic) NSMutableArray *data;

- (void)loadPageScroller:(NSMutableArray *)targets interactable:(BOOL) interactable target:(id) caller;

@end
