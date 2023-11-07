//
//  ViewController.h
//  PS4_MacHell
//
//  Created by student on 07/11/2023.
//  Copyright © 2023 me.jorlowski. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SecondViewController.h"

@interface ViewController : UIViewController <SecondViewControllerDelegate>

@property (nonatomic, weak) IBOutlet UITextField *inputField;
@property (nonatomic, weak) IBOutlet UILabel *messageLabel;
@property (nonatomic, weak) IBOutlet UITextField *surnameField;
-(IBAction) enter;

@end


