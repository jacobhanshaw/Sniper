//
//  CameraOverlayView.m
//  Sniper
//
//  Created by Jacob Hanshaw on 12/1/12.
//  Copyright (c) 2012 Jacob Hanshaw. All rights reserved.
//

#import "CameraOverlayView.h"

@implementation CameraOverlayView

@synthesize delegate, picker;

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        // Initialization code
    }
    return self;
}

-(void) update{
    zoomLevelSlider.hidden = YES;
}

- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event {
    [self.delegate takePicture];
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
