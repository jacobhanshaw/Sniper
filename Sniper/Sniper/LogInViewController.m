//
//  LogInViewController.m
//  Sniper
//
//  Created by Jacob Hanshaw on 12/1/12.
//  Copyright (c) 2012 Jacob Hanshaw. All rights reserved.
//

#import "LogInViewController.h"
#import "Target.h"
#import "AFNetworking.h"

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
    [AppModel sharedAppModel].user.name = nameTextField.text;
    [AppModel sharedAppModel].user.password = passwordTextField.text;
    
    NSURL *url = [NSURL URLWithString:@"http://winrar.upl.cs.wisc.edu:9876"];
    AFHTTPClient *httpClient = [[AFHTTPClient alloc] initWithBaseURL:url];
    
    NSMutableDictionary *parameters = [NSMutableDictionary dictionary];
    [parameters setObject:[AppModel sharedAppModel].user.name forKey:@"username"];
    [parameters setObject:[AppModel sharedAppModel].user.password forKey:@"password"];
    
    NSMutableURLRequest *request = [httpClient requestWithMethod:@"POST" path:@"/login" parameters:parameters];
    
    AFHTTPRequestOperation *operation = [[AFHTTPRequestOperation alloc] initWithRequest:request];

    [operation start];
    
    //CONFIRM PROPER LOG IN HERE
    timer = [NSTimer scheduledTimerWithTimeInterval:10 target:self selector:@selector(checkForMission) userInfo:nil repeats:YES];
    [self checkForMission];
}

- (void) checkForMission {
    NSURL *url = [NSURL URLWithString:@"http://winrar.upl.cs.wisc.edu:9876/mission?id=1"];
    NSURLRequest *request = [NSURLRequest requestWithURL:url];
    
    AFJSONRequestOperation *operation = [AFJSONRequestOperation JSONRequestOperationWithRequest:request success:^(NSURLRequest *request, NSHTTPURLResponse *response, id JSON) {
        
        [self receiveMissionWithJSON:JSON];
        
    } failure:nil];
    
    [operation start];
    
}

- (void) receiveMissionWithJSON: (id) JSON {
    
    NSDictionary *mission = [[NSDictionary alloc] init];
    mission = [JSON valueForKeyPath:@"mission"];
    
    Target *currentTarget = [[Target alloc] init];
    currentTarget.name = [mission valueForKeyPath:@"name"];
    
    
    UIImage *image = [UIImage imageNamed:@"player.png"];
    NSString *imageLocation = [NSString stringWithFormat:@"http://winrar.upl.cs.wisc.edu:9876/static/media/img_%@.jpg", [mission valueForKeyPath:@"id"]];
    NSURL *imageURL = [NSURL URLWithString:imageLocation];
    [currentTarget.imageView setImageWithURL:imageURL placeholderImage:image];
    [[AppModel sharedAppModel].currentGame.currentTargets addObject:currentTarget];
   // [AppModel sharedAppModel].missionNumber = [[mission valueForKey:@""] intValue];
   // [AppModel sharedAppModel].targetsLeft = [[JSON valueForKeyPath:@"alive_count"] intValue];
    
    
    [timer invalidate];
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
