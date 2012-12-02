//
//  RootViewController.m
//  ProjectReality
//
//  Created by Jacob Hanshaw on 10/19/12.
//  Copyright (c) 2012 Jacob Hanshaw. All rights reserved.
//

#import "RootViewController.h"


@implementation RootViewController

@synthesize startGame;

+ (id)sharedRootViewController
{
    static dispatch_once_t pred = 0;
    __strong static id _sharedObject = nil;
    dispatch_once(&pred, ^{
        _sharedObject = [[self alloc] initWithFrame:[UIScreen mainScreen].bounds];//[UIScreen mainScreen].bounds]; // or some other init method
    });
    return _sharedObject;
}

- (id)initWithFrame:(CGRect)frame {
    self = [super init];
    if (self) {
        self.view.frame = frame;
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
    
    [[UIApplication sharedApplication] setStatusBarHidden:YES withAnimation:UIStatusBarAnimationNone];
    
    [self.startGame setTitle:@"SHOOT HER!" forState:UIControlStateNormal];
    [self.startGame addTarget:self action:@selector(startGameSelected:) forControlEvents:UIControlEventTouchUpInside];
    
}

- (void) viewDidAppear:(BOOL)animated {
    if([[AppModel sharedAppModel].name isEqualToString:@""] || [AppModel sharedAppModel].name == nil){
        LogInViewController *login = [[LogInViewController alloc] init];
        [self presentViewController:login animated:YES completion:nil];
    }
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


-(void) startGameSelected:(id)sender{
 /*
    NSURL *url = [NSURL URLWithString:@"http://winrar.upl.cs.wisc.edu:9876/groups"];
    NSURLRequest *request = [NSURLRequest requestWithURL:url];
    
    AFJSONRequestOperation *operation = [AFJSONRequestOperation JSONRequestOperationWithRequest:request success:^(NSURLRequest *request, NSHTTPURLResponse *response, id JSON) {
        NSLog(@"Name: %@", [JSON valueForKeyPath:@"name"]);
    } failure:nil];
    
    [operation start]; */
    
    CameraViewController *cameraView = [[CameraViewController alloc] init];
    [self presentViewController:cameraView animated:YES completion:nil];
}

@end
