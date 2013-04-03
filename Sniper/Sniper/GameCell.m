//
//  GameCell.m
//  Sniper
//
//  Created by Jacob Hanshaw on 3/29/13.
//  Copyright (c) 2013 Jacob Hanshaw. All rights reserved.
//

#import "GameCell.h"
#import "TargetScrollView.h"

#define LABELOFFSETX  5
#define LABELOFFSETY  5
#define LABELWIDTH    160
#define LABELHEIGHT   20
#define SCROLLOFFSETX 5
#define SCROLLOFFSETY 20
#define SCROLLWIDTH   5
#define SCROLLHEIGHT  20

@implementation GameCell

@synthesize game, interactable, section, row, caller;

- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        // Initialization code
    }
    return self;
}

- (id)initWithFrame: (CGRect) frame Game: (Game *) inputGame interactableButtons:(BOOL) inputInteractable caller:(id) inputCaller section:(int) inputSection row:(int) inputRow reuseIdentifier:(NSString *) reuseIdentifier
{
    self = [super initWithStyle:UITableViewCellStyleDefault reuseIdentifier:reuseIdentifier];
    if (self) {
        self.frame = frame;
        self.backgroundColor = [UIColor blackColor];
        self.contentView.backgroundColor = [UIColor blackColor];
        self.selectionStyle = UITableViewCellSelectionStyleNone;
        
        self.textLabel.hidden = YES;
        self.detailTextLabel.hidden = YES;
        
        game = inputGame;
        caller = inputCaller;
        section = inputSection;
        row = inputRow;
        interactable = inputInteractable;
        
        nameLabel = [[UILabel alloc] initWithFrame:CGRectMake(LABELOFFSETX, LABELOFFSETY, LABELWIDTH, LABELHEIGHT)];
        nameLabel.textColor = [UIColor whiteColor];
        nameLabel.backgroundColor = [UIColor blackColor];
        nameLabel.text = game.name;
        [self addSubview:nameLabel];
        
        typeLabel = [[UILabel alloc] initWithFrame:CGRectMake(self.frame.size.width - LABELWIDTH - LABELOFFSETX, LABELOFFSETY, LABELWIDTH, LABELHEIGHT)];
        typeLabel.textColor = [UIColor whiteColor];
        typeLabel.backgroundColor = [UIColor blackColor];
        typeLabel.textAlignment = UITextAlignmentRight;
        typeLabel.text = stringWithGameType(game.type);
        [self addSubview:typeLabel];
        
        scrollView = [[TargetScrollView alloc] initWithFrame:CGRectMake(SCROLLOFFSETX, LABELOFFSETY + LABELHEIGHT + SCROLLOFFSETY, self.frame.size.width - 2 * SCROLLOFFSETX, self.frame.size.height - (LABELOFFSETY + LABELHEIGHT + 2 * SCROLLOFFSETY))];
        [scrollView loadPageScroller:game.allTargets interactable:interactable target:caller section:inputSection row: inputRow];
        [self addSubview:scrollView];
        
    }
    return self;
}

- (void)updateWithGame:(Game *) inputGame section:(int) inputSection andRow:(int) inputRow{
    game = inputGame;
    nameLabel.text = game.name;
    typeLabel.text = stringWithGameType(game.type);
    
    [scrollView loadPageScroller:game.allTargets interactable:interactable target:caller section:inputSection row: inputRow];
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
