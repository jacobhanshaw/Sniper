//
//  LogInViewController.h
//  Sniper
//
//  Created by Jacob Hanshaw on 12/1/12.
//  Copyright (c) 2012 Jacob Hanshaw. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "AppModel.h"
#import "AFHTTPClient.h"
#import "AFHTTPRequestOperation.h"
#import "AFJSONRequestOperation.h"

@interface LogInViewController : UIViewController <UITextFieldDelegate> {
    
    IBOutlet UITextField *nameTextField;
    IBOutlet UITextField *passwordTextField;
    IBOutlet UIButton *continueButton;
    NSTimer *timer;
}

@property (nonatomic) IBOutlet UITextField *nameTextField;
@property (nonatomic) IBOutlet UITextField *passwordTextField;
@property (nonatomic) IBOutlet UIButton *continueButton;

- (IBAction)continueButtonPressed:(id)sender;

@end
