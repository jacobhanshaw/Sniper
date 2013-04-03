//
//  GameCell.h
//  Sniper
//
//  Created by Jacob Hanshaw on 3/29/13.
//  Copyright (c) 2013 Jacob Hanshaw. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "TargetScrollView.h"
#import "Game.h"

@interface GameCell : UITableViewCell {
    UILabel *nameLabel;
    UILabel *typeLabel;
    TargetScrollView *scrollView;
    
    Game     *game;
    BOOL      interactable;
    int       section;
    int       row;
    id        caller;
}

@property (nonatomic) Game     *game;
@property (readwrite) BOOL      interactable;
@property (readwrite) int       section;
@property (readwrite) int       row;
@property (nonatomic) id        caller;

- (id)initWithFrame: (CGRect) frame Game: (Game *) inputGame interactableButtons:(BOOL) inputInteractable caller:(id) inputCaller section:(int) inputSection row:(int) inputRow reuseIdentifier:(NSString *) reuseIdentifier;
- (void)updateWithGame:(Game *) inputGame section:(int) inputSection andRow:(int) inputRow;

@end
