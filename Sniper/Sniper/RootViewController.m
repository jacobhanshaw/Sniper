//
//  RootViewController.m
//  ProjectReality
//
//  Created by Jacob Hanshaw on 10/19/12.
//  Copyright (c) 2012 Jacob Hanshaw. All rights reserved.
//

#import "RootViewController.h"
#import "AppModel.h"
#import "MissionViewController.h"
#import "AFJSONRequestOperation.h"
#import "LogInViewController.h"
#import "CameraOverlayView.h"

@implementation RootViewController

//ERROR: MOVE TO MODEL
@synthesize machineGunBulletsLeft, shots, zoom;

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
      //  NSArray* controllers = [NSArray arrayWithObjects:nc1, nc2, nc3, nil];
      //  self.viewControllers = controllers;
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
    
    [[UIApplication sharedApplication] setStatusBarHidden:YES withAnimation:UIStatusBarAnimationNone];
    targetTableView = [[HorizontalTableView alloc] initWithFrame:CGRectMake(20, 100, 280, 100)];
    targetTableView.delegate = self;
    targetTableView.dataSource = self;
    [self.view addSubview:targetTableView];
    
    UIButton* button = [UIButton buttonWithType:UIButtonTypeCustom];
    UIImage *buttonImage = [UIImage imageNamed:@"targetReticle.png"];
    UIImage *highlightImage = [UIImage imageNamed:@"targetReticle.png"];
    button.autoresizingMask = UIViewAutoresizingFlexibleRightMargin | UIViewAutoresizingFlexibleLeftMargin | UIViewAutoresizingFlexibleBottomMargin | UIViewAutoresizingFlexibleTopMargin;
    button.frame = CGRectMake(0.0, 0.0, buttonImage.size.width, buttonImage.size.height);
    [button setBackgroundImage:buttonImage forState:UIControlStateNormal];
    [button setBackgroundImage:highlightImage forState:UIControlStateHighlighted];
    
    CGFloat heightDifference = buttonImage.size.height - self.tabBar.frame.size.height;
    if (heightDifference < 0)
        button.center = self.tabBar.center;
    else
    {
        CGPoint center = self.tabBar.center;
        center.y = center.y - heightDifference/2.0;
        button.center = center;
    }
    
    [button addTarget:self action:@selector(presentCamera) forControlEvents:UIControlEventTouchUpInside];
    
    [self.view addSubview:button];
    
    picker = [[UIImagePickerController alloc] init];
    picker.sourceType = UIImagePickerControllerSourceTypeCamera;
    //   picker.modalTransitionStyle = UIModalTransitionStyleFlipHorizontal;
    picker.showsCameraControls = NO;
    picker.wantsFullScreenLayout = YES;
    picker.delegate = self;
    //ERROR: FIX:
   // [[NSNotificationCenter defaultCenter] addObserver: self selector: @selector(unique) name: @"VolumeButtonPressed" object: nil];
    
    NSArray *nibs = [[NSBundle mainBundle] loadNibNamed:@"CameraOverlayView"
                                                  owner:nil
                                                options:nil];
    if([nibs count]){
        id nib = [nibs objectAtIndex:0];
        if([nib isKindOfClass:[CameraOverlayView class]]){
            CameraOverlayView *view = (CameraOverlayView*)nib;
            view.delegate = self;
            view.picker = picker;
            [view update];
            picker.cameraOverlayView = view;
        }
    }
}

- (void) viewDidAppear:(BOOL)animated {
  /*  if([[AppModel sharedAppModel].name isEqualToString:@""] || [AppModel sharedAppModel].name == nil){
        LogInViewController *login = [[LogInViewController alloc] init];
        [self presentViewController:login animated:YES completion:nil];
    }
    else{
    MissionViewController *missionView = [[MissionViewController alloc] init];
    [self presentViewController:missionView animated:YES completion:nil];
    }*/
}

#pragma mark Camera Methods

- (void) presentCamera {
    [self presentViewController:picker animated:YES completion:nil];
}

- (void)takePicture {
    [picker takePicture];
}

#pragma mark TableView DataSource and Delegate Methods

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}


- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return 25;
}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    NSString *CellIdentifier = @"Cell";
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier];
    }
    
    cell.textLabel.text = [NSString stringWithFormat:@"This is row: %d", indexPath.row];
    cell.transform = CGAffineTransformMakeRotation(M_PI * 0.5);
    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
