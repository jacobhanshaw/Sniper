//
//  TargetScrollView.m
//  Sniper
//
//  Created by Jacob Hanshaw on 3/23/13.
//  Copyright (c) 2013 Jacob Hanshaw. All rights reserved.
//

#import "TargetScrollView.h"
#import "Target.h"

#define SPACEBETWEENTARGETS       0
//#define NORMALTARGETWIDTH        80
//#define NORMALTARGETHEIGHT       80
//#define HIGHLIGHTEDTARGETWIDTH  160
//#define HIGHLIGHTEDTARGETHEIGHT 160

@implementation TargetScrollView

@synthesize data;

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        self.scrollEnabled = YES;
        self.showsHorizontalScrollIndicator = NO;
        self.backgroundColor = [UIColor blackColor]; //clearColor
        self.bounces = NO;
    }
    return self;
}

- (void)loadPageScroller:(NSMutableArray *)targets interactable:(BOOL) interactable
{
    int spacing = SPACEBETWEENTARGETS; //page spacing
    int margin =  SPACEBETWEENTARGETS/2; //side margins
    
    for (int i = 0; i <= targets.count; i++) {
        float x = i * (self.frame.size.height+spacing) + margin;
        
        UIButton *targetButton = [UIButton buttonWithType:UIButtonTypeCustom];
        targetButton.userInteractionEnabled = interactable;
        
        targetButton.frame = CGRectMake(x, 0, self.frame.size.height, self.frame.size.height);
        targetButton.backgroundColor = [UIColor blackColor]; //clearColor
        
        [targetButton setBackgroundImage:((Target *)[data objectAtIndex:i]).imageView.image forState:UIControlStateNormal];
        [targetButton setBackgroundImage:((Target *)[data objectAtIndex:i]).imageView.image forState:UIControlStateHighlighted];
        [targetButton addTarget:self action:@selector(targetButtonPressed:) forControlEvents:UIControlEventTouchUpInside];
        targetButton.tag = i;
        [self addSubview:targetButton];
        
        //add page number labels
        UILabel *label =  [[UILabel alloc] initWithFrame: CGRectMake(x, self.frame.size.height * (2/3), self.frame.size.height, self.frame.size.height * (1/3))];
        label.text = [NSString stringWithFormat:@"%d", i];
        label.backgroundColor = [UIColor clearColor];
        label.textColor = [UIColor grayColor];
        label.textAlignment = UITextAlignmentCenter;
        label.font = [UIFont systemFontOfSize:13];
        
        [targetButton addSubview:label];
    }
    //pageScrollerWidth = ((data.count-1) * (self.frame.size.height+spacing) + margin + (margin +self.frame.size.height))-320;
    [self setContentSize:CGSizeMake(data.count * (self.frame.size.height+spacing), self.frame.size.height)];
    
    /*  if(currentIndex > 4 && currentIndex <= [pages count]-6)
     [pageScroller setContentOffset:CGPointMake(currentIndex * (currentSelectedPageIcon.frame.size.width + SPACEBETWEENPAGESINBOTTOMBAR) - ([[UIScreen mainScreen] bounds].size.width/2 - (currentSelectedPageIcon.frame.size.width/2) - (SPACEBETWEENPAGESINBOTTOMBAR/2)), 0) animated:YES];
     else if (currentIndex <= 4)
     [pageScroller setContentOffset:CGPointMake(0, 0) animated:YES];
     else
     [[pageScroller setContentOffset:CGPointMake(([pages count]-5) * (currentSelectedPageIcon.frame.size.width + SPACEBETWEENPAGESINBOTTOMBAR) - ([[UIScreen mainScreen] bounds].size.width/2 - (currentSelectedPageIcon.frame.size.width/2) - (SPACEBETWEENPAGESINBOTTOMBAR/2)), 0) animated:YES];*/
}

- (void)scrollViewDidEndDecelerating:(UIScrollView *)scrollView {
    if((int)self.contentOffset.x%(int)(self.frame.size.height+SPACEBETWEENTARGETS) >= (self.frame.size.height+SPACEBETWEENTARGETS)/2){
        [self setContentOffset:CGPointMake(((int)self.contentOffset.x%(int)(self.frame.size.height+SPACEBETWEENTARGETS) + 1) * (self.frame.size.height+SPACEBETWEENTARGETS),0) animated:YES];
    }
    else{
        [self setContentOffset:CGPointMake((int)self.contentOffset.x%(int)(self.frame.size.height+SPACEBETWEENTARGETS) * (self.frame.size.height+SPACEBETWEENTARGETS),0) animated:YES];
    }
   /* if(self.contentOffset.x > 4 self.c && self.contentOffset.x <= ([data count]-6) * ((self.frame.size.height+SPACEBETWEENTARGETS)))
        [self setContentOffset:CGPointMake(currentIndex * (currentSelectedPageIcon.frame.size.width + SPACEBETWEENPAGESINBOTTOMBAR) - ([[UIScreen mainScreen] bounds].size.width/2 - (currentSelectedPageIcon.frame.size.width/2) - (SPACEBETWEENPAGESINBOTTOMBAR/2)), 0) animated:YES];
    else if (currentIndex <= 4)
        [self setContentOffset:CGPointMake(0, 0) animated:YES];
    else
        [[pageScroller setContentOffset:CGPointMake(([pages count]-5) * (currentSelectedPageIcon.frame.size.width + SPACEBETWEENPAGESINBOTTOMBAR) - ([[UIScreen mainScreen] bounds].size.width/2 - (currentSelectedPageIcon.frame.size.width/2) - (SPACEBETWEENPAGESINBOTTOMBAR/2)), 0) animated:YES];*/
}

/*
-(void)refreshCurrentSpeed
{
    float currentOffset = self.contentOffset.x;
    NSTimeInterval currentTime = [[NSDate date] timeIntervalSince1970];
    
    deltaOffset = (currentOffset - prevOffset);
    deltaTime = (currentTime - prevTime);
    currentSpeed = deltaOffset/deltaTime;
    prevOffset = currentOffset;
    prevTime = currentTime;
    
    NSLog(@"deltaOffset is now %f, deltaTime is now %f and speed is %f",deltaOffset,deltaTime,currentSpeed);
}

-(void)snapIfNeeded
{
    if(canStopScrolling && currentSpeed <70.0f && currentSpeed>-70.0f)
    {
        NSLog(@"Stopping with a speed of %f points per second", currentSpeed);
        [self stopMoving];
        
        float scrollDistancePastTabStart = fmodf(self.contentOffset.x, (self.frame.size.width/3));
        float scrollSnapX = self.contentOffset.x - scrollDistancePastTabStart;
        if(scrollDistancePastTabStart > self.frame.size.width/6)
        {
            scrollSnapX += self.frame.size.width/3;
        }
        float maxSnapX = self.contentSize.width-self.frame.size.width;
        if(scrollSnapX>maxSnapX)
        {
            scrollSnapX = maxSnapX;
        }
        [UIView animateWithDuration:0.3
                         animations:^{self.contentOffset=CGPointMake(scrollSnapX, self.contentOffset.y);}
                         completion:^(BOOL finished){[self stopMoving];}
         ];
    }
    else
    {
        NSLog(@"Did not stop with a speed of %f points per second", currentSpeed);
    }
}

-(void)stopMoving
{
    if(self.dragging)
    {
        [self setContentOffset:CGPointMake(self.contentOffset.x, self.contentOffset.y) animated:NO];
    }
    canStopScrolling = NO;
}


-(void)scrollViewWillBeginDragging:(UIScrollView *)scrollView
{
    canStopScrolling = NO;
    [self refreshCurrentSpeed];
}

-(void)scrollViewDidEndDragging:(UIScrollView *)scrollView willDecelerate:(BOOL)decelerate
{
    canStopScrolling = YES;
    NSLog(@"Did end dragging");
    [self snapIfNeeded];
}

- (void)scrollViewDidScroll:(UIScrollView *)scrollView
{
    [self refreshCurrentSpeed];
    [self snapIfNeeded];
}


 // Only override drawRect: if you perform custom drawing.
 // An empty implementation adversely affects performance during animation.
 - (void)drawRect:(CGRect)rect
 {
 // Drawing code
 }
 */

@end
