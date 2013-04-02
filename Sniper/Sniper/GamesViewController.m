//
//  GamesViewController.m
//  Sniper
//
//  Created by Jacob Hanshaw on 3/13/13.
//  Copyright (c) 2013 Jacob Hanshaw. All rights reserved.
//

#import "GamesViewController.h"
#import "HorizontalTableViewCell.h"
#import "Target.h"

@interface GamesViewController ()

@end

@implementation GamesViewController

@synthesize reusableCells;

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
    /*
    if (!self.reusableCells)
    {
        
        self.reusableCells = [NSMutableArray array];
        
        for (int i = 0; i < 2; i++)
        {
            HorizontalTableViewCell *cell = [[HorizontalTableViewCell alloc] initWithFrame:CGRectMake(0, 0, 320, 416)];
            
            [self.reusableCells addObject:cell];
        }
    }*/
    //gamesTableView = [[HorizontalTableView alloc] init];
    gamesScrollView = [[TargetScrollView alloc] initWithFrame:CGRectMake(20, 80, 280, 80)];
    NSMutableArray *targets = [[NSMutableArray alloc] init];
    for(int i = 0; i < 20; ++i){
        Target *targetA = [[Target alloc] init];
        targetA.imageView = [[UIImageView alloc] initWithImage: [UIImage imageNamed:@"player.png"]];
        targetA.name = [NSString stringWithFormat:@"Hello %c", ('A' + i)];
        [targets addObject:targetA];
    }
    
    [gamesScrollView loadPageScroller:targets interactable:YES target:self];
  //  gamesTableView = [[HorizontalTableView alloc]initWithFrame:CGRectMake(0, 80, 280, 200)];
 //   [self.view addSubview:gamesTableView];
    [self.view addSubview:gamesScrollView];
}

- (IBAction)targetButtonPressed:(id) sender{
    UIButton *button = (UIButton *)sender;
    NSLog(@"Clicked target at index: %d", button.tag);
}

/*
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}


- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return 2; //[data count];
}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    HorizontalTableViewCell *cell = [self.reusableCells objectAtIndex:indexPath.section];
    return cell;
}


//callback to the parent with the name of the table and the selection
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
}

*/
- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)viewDidUnload {
    gamesTableView = nil;
    [super viewDidUnload];
}
@end
