//
//  Header.h
//  HorizontalTables
//
//  Created by Felipe Laso on 8/19/11.
//  Copyright 2011 Felipe Laso. All rights reserved.
//

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// iPhone CONSTANTS
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

// Width (or length before rotation) of the table view embedded within another table view's row
#define kTableLength                                320

// Width of the cells of the embedded table view (after rotation, which means it controls the rowHeight property)
#define kCellWidth                                  100
// Height of the cells of the embedded table view (after rotation, which would be the table's width)
#define kCellHeight                                 50

// Padding for the Cell containing the Target image and title
#define kTargetCellVerticalInnerPadding            0
#define kTargetCellHorizontalInnerPadding          0

//Label Height
#define kTargetCellLabelHeight                     20
#define kTargetCellBackgroundColor                 [UIColor blackColor]
#define kTargetCellTextColor                       [UIColor redColor]

// Padding for the title label in an Target's cell
#define kTargetTitleLabelPadding                   0

// Vertical padding for the embedded table view within the row
#define kRowVerticalPadding                         0
// Horizontal padding for the embedded table view within the row
#define kRowHorizontalPadding                       0

// The background color of the vertical table view
#define kVerticalTableBackgroundColor               [UIColor blackColor]

// Background color for the horizontal table view (the one embedded inside the rows of our vertical table)
#define kHorizontalTableBackgroundColor             [UIColor blackColor]

// The background color on the horizontal table view for when we select a particular cell
#define kHorizontalTableSelectedBackgroundColor     [UIColor blackColor]

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// iPad CONSTANTS
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

// Width (or length before rotation) of the table view embedded within another table view's row
#define kTableLength_iPad                               768

// Height for the Headlines section of the main (vertical) table view
#define kHeadlinesSectionHeight_iPad                    65

// Height for regular sections in the main table view
#define kRegularSectionHeight_iPad                      36

// Width of the cells of the embedded table view (after rotation, which means it controls the rowHeight property)
#define kCellWidth_iPad                                 192
// Height of the cells of the embedded table view (after rotation, which would be the table's width)
#define kCellHeight_iPad                                192

// Padding for the Cell containing the Target image and title
#define kTargetCellVerticalInnerPadding_iPad           4
#define kTargetCellHorizontalInnerPadding_iPad         4

// Vertical padding for the embedded table view within the row
#define kRowVerticalPadding_iPad                        0
// Horizontal padding for the embedded table view within the row
#define kRowHorizontalPadding_iPad                      0
