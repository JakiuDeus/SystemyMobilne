//
//  ViewController.m
//  PS4_MacHell
//
//  Created by student on 07/11/2023.
//  Copyright © 2023 me.jorlowski. All rights reserved.
//

#import "ViewController.h"
#import "SecondViewController.h"

@interface ViewController ()

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
}

-(IBAction)enter {
    NSString *yourName = _inputField.text;
    NSString *myName = @"Jakub";
    NSString *message;
    if([yourName length] == 0) {
        yourName = @"World";
    }
    if([yourName isEqualToString:myName]) {
        message = [NSString stringWithFormat:@"Hello %@! We have the same name :)", yourName];
    } else {
        message = [NSString stringWithFormat:@"Hello %@!", yourName];
    }
    _messageLabel.text = message;
}

-(void) prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    if ([segue.identifier isEqualToString:@"sendSurnameSegue"]) {
        SecondViewController *controller = (SecondViewController *) segue.destinationViewController;
        controller.delegate = self;
        controller.surname = _surnameField.text;
    }
}

-(void) addItemViewController:(SecondViewController *) controller didFinishEnteringItem:(NSString *)item {
    NSLog(@"This was returned from SecondViewController %@", item);
    _surnameField.text = item;
}

@end
