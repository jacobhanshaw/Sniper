//
//  GamesViewController.m
//  Sniper
//
//  Created by Jacob Hanshaw on 3/13/13.
//  Copyright (c) 2013 Jacob Hanshaw. All rights reserved.
//

#import "GamesViewController.h"
//#import "HorizontalTableViewCell.h"
#import "Target.h"
#import "Game.h"
#import "GameCell.h"
#import "ExtraTagButton.h"

#define CELLHEIGHT 120

@interface GamesViewController ()

@end

@implementation GamesViewController

//@synthesize reusableCells;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        self.title = @"Games";
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
    gamesTableView = [[UITableView alloc] initWithFrame:CGRectMake(0, 44, 320, 316)];
    gamesTableView.delegate = self;
    gamesTableView.dataSource = self;
    gamesTableView.bounces = NO;
    games = [[NSMutableArray alloc] init];
   // gamesScrollView = [[TargetScrollView alloc] initWithFrame:CGRectMake(20, 80, 280, 80)];
    NSMutableArray *targets = [[NSMutableArray alloc] init];
    for(int i = 0; i < 20; ++i){
        Target *targetA = [[Target alloc] init];
        targetA.imageView = [[UIImageView alloc] initWithImage: [UIImage imageNamed:@"player.png"]];
        targetA.name = [NSString stringWithFormat:@"Hello %c", ('A' + i)];
        [targets addObject:targetA];
    }
    for(int i = 0; i < 20; ++i){
        Game *currentGame = [[Game alloc] init];
        currentGame.name = [NSString stringWithFormat:@"Game Name %c", 'A' + i];
        currentGame.type = ASSASSINS;
        currentGame.gameId = i;
        currentGame.currentTargets = [[NSMutableArray alloc] init];
        currentGame.allTargets = [targets copy];
        [games addObject:currentGame];
    }
    
 //   [gamesScrollView loadPageScroller:targets interactable:YES target:self];
    [self.view addSubview:gamesTableView];
 //   [self.view addSubview:gamesScrollView];
}

- (IBAction)targetButtonPressed:(id) sender{
    ExtraTagButton *button = (ExtraTagButton *)sender;
    NSLog(@"Clicked target at index: %d", button.tag);
    
    [self tableView:gamesTableView didSelectRowAtIndexPath:[NSIndexPath indexPathForRow:button.row inSection:0]];
}


- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}


- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return [games count]; //[data count];
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return CELLHEIGHT;
}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    GameCell *cell = [tableView dequeueReusableCellWithIdentifier:@"GameCell"];
    
    if(!cell) cell = [[GameCell alloc] initWithFrame: CGRectMake(0, 0, 320, CELLHEIGHT) Game: [games objectAtIndex:indexPath.row] interactableButtons: YES caller: self section: indexPath.section row: indexPath.row reuseIdentifier:@"GameCell"];
    
    else [cell updateWithGame:[games objectAtIndex:indexPath.row] section:indexPath.section andRow:indexPath.row];
    
    return cell;
}


//callback to the parent with the name of the table and the selection
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    [tableView deselectRowAtIndexPath:indexPath animated:NO];
    NSLog(@"Clicked: Section: %d Row: %d", indexPath.section, indexPath.row);
}

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
