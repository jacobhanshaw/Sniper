//
//  LogInViewController.m
//  Sniper
//
//  Created by Jacob Hanshaw on 12/1/12.
//  Copyright (c) 2012 Jacob Hanshaw. All rights reserved.
//

#import "LogInViewController.h"

@implementation LogInViewController

@synthesize nameTextField, passwordTextField, continueButton;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
    nameTextField.delegate = self;
    passwordTextField.delegate = self;
    passwordTextField.secureTextEntry = YES;
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)viewDidUnload {
    nameTextField = nil;
    passwordTextField = nil;
    continueButton = nil;
    [super viewDidUnload];
}

- (IBAction)continueButtonPressed:(id)sender {
    [nameTextField resignFirstResponder];
    [passwordTextField resignFirstResponder];
    [AppModel sharedAppModel].name = nameTextField.text;
    [AppModel sharedAppModel].password = passwordTextField.text;
    
    [self dismissViewControllerAnimated:YES completion:nil];
}

#pragma mark TextField Methods

- (BOOL)textFieldShouldReturn:(UITextField *)textField
{
    [textField resignFirstResponder];
    return YES;
}

//Makes keyboard disappear on touch outside of keyboard or textfield, only used when an input view thingy is visible
- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event
{
    [nameTextField resignFirstResponder];
    [passwordTextField resignFirstResponder];
} 

@end
