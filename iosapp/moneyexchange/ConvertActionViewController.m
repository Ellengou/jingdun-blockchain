//
//  ConvertActionViewController.m
//  moneyexchange
//
//  Created by dapeng on 16/10/16.
//  Copyright © 2016年 qukuailian. All rights reserved.
//

#import "ConvertActionViewController.h"
#import "ReceivedAccountDetailTableViewCell.h"
#import "SectionHeaderDescTableViewCell.h"
#import "SectionFooterActionTableViewCell.h"
#import "QKLLanguages.h"
#import "QKLUtils.h"
#import "ConvertDetailTableViewCell.h"
#import "Pingpp.h"
#import "PayPalMobile.h"

@interface ConvertActionViewController ()<UITableViewDelegate, UITableViewDataSource, SectionFooterActionDelegate, PayPalPaymentDelegate> {
    NSNumber *mpayorder;
}
@property (nonatomic, strong, readwrite) PayPalConfiguration *payPalConfiguration;

@end

@implementation ConvertActionViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self createPaypalConfig];
    // Do any additional setup after loading the view from its nib.
}

- (void)viewWillAppear:(BOOL)animated {
    
    #warning ----------------上线的时候修改成正式环境----------------
    // Start out working with the test environment! When you are ready, switch to PayPalEnvironmentProduction.
    //    [PayPalMobile preconnectWithEnvironment:PayPalEnvironmentSandbox];
    [PayPalMobile preconnectWithEnvironment:PayPalEnvironmentProduction];
}

#pragma mark UITableViewDelegate, UITableViewDataSource
-(NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 2;
}

-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    if (section == 0) {
        return _infoNodeList.count;
    }
    return 2;
}

-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    if (indexPath.section == 0) {
        return 122.0f;
    } else {
        if (indexPath.row == 0) {
            return 170;
        } else {
            return 120;
        }
    }
}

-(CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section {
    return 45.0f;
}

-(UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section {
    SectionHeaderDescTableViewCell *header = (SectionHeaderDescTableViewCell *)([[UIViewController alloc] initWithNibName:@"SectionHeaderDescTableViewCell" bundle:nil].view);
    if (section == 0) {
        header.labelDesc.text = lang_need_desc;
    } else {
        header.labelDesc.text = lang_transfer_desc;
    }
    return header;
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    if (indexPath.section == 0) {
        ConvertDetailTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"ConvertDetailTableViewCellID"];
        if (!cell) {
            UIViewController *vc = [[UIViewController alloc] initWithNibName:@"ConvertDetailTableViewCell" bundle:nil];
            cell = (ConvertDetailTableViewCell *)vc.view;
        }
        [cell configCellWithInfo:[_infoNodeList objectAtIndex:indexPath.row]];
        cell.btnConvert.hidden = YES;
        return cell;
    } else {
        if (indexPath.row == 0) {
            ReceivedAccountDetailTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"ReceivedAccountDetailTableViewCellID"];
            if (!cell) {
                UIViewController *vc = [[UIViewController alloc] initWithNibName:@"ReceivedAccountDetailTableViewCell" bundle:nil];
                cell = (ReceivedAccountDetailTableViewCell *)vc.view;
            }
            ConvertMoneyType type = ConvertMoneyTypeCNY;
            CGFloat money = 0.0f;
            for (QKLConvertInfoNode *node in _infoNodeList) {
                type = node.stype;
                money += node.money;
            }
            [cell configCellType:type money:money];
            return cell;
        } else {
            SectionFooterActionTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"SectionFooterActionTableViewCellID"];
            if (!cell) {
                UIViewController *vc = [[UIViewController alloc] initWithNibName:@"SectionFooterActionTableViewCell" bundle:nil];
                cell = (SectionFooterActionTableViewCell *)vc.view;
                cell.delegate = self;
            }
            cell.actionType = ActionTypeSureConvert;
            return cell;
        }
    }
}

#pragma mark SectionFooterActionDelegate method
- (void)sectionFooterAction:(ActionType)actionType {
    NSMutableString *itemids = [NSMutableString string];
    for (QKLConvertInfoNode *node in _infoNodeList) {
        [itemids appendString:[NSString stringWithFormat:@"%ld,", (long)node.itemid]];
    }
    if (itemids.length == 0) {
        return;
    }
    [itemids deleteCharactersInRange:NSMakeRange(itemids.length - 1, 1)];
    
    [[QKLServerClient sharedNetworking] getWithUrl:server_api_default params:[QKLUtils completeUserToken:@{method_sel: method_sel_orderadd, tag_itemids: [itemids copy]}] success:^(id response) {
        if ([QKLUtils getResponseCode:response] == 0) {
            NSDictionary *data = [response objectForKey:tag_data];
            if (data) {
                NSLog(@"%@",data);
                mpayorder = [data objectForKey:tag_payorder];
                NSDictionary *pingppCharge = [data objectForKey:tag_chargedata];
                //如果method_sel_orderadd方法返回的数据中包含chargedata，调用ping++付款，否则调用paypal
                if (pingppCharge) {
                    [self createPingPPPayment:pingppCharge];
                }else{
                    NSDictionary *paypalCharge = @{tag_paycost:[data objectForKey:tag_paycost], tag_paystype:[data objectForKey:tag_paystype]};
                    [self createPaypalPayment:paypalCharge];
                }
            }
        } else {
            [self.view makeToast:[QKLUtils getResponseMsg:response]];
        }
    } fail:^(NSError *error) {
        [self.view makeToast:lang_net_err];
    } showHUD:self.view];
}


#pragma mark click handler
- (IBAction)clickBack:(id)sender {
    [self.navigationController popViewControllerAnimated:YES];
}

#pragma mark - PayPalPaymentDelegate
- (void)payPalPaymentViewController:(nonnull PayPalPaymentViewController *)paymentViewController
                 didCompletePayment:(nonnull PayPalPayment *)completedPayment {
    
    [self handlePayResult:@0 withPayOrder:mpayorder withConfirmation:completedPayment.confirmation];
    [self.view makeToast:@"支付成功！"];
    [self dismissViewControllerAnimated:YES completion:nil];
}

- (void)payPalPaymentDidCancel:(nonnull PayPalPaymentViewController *)paymentViewController {
    [self.view makeToast:@"支付失败！"];
    [self dismissViewControllerAnimated:YES completion:nil];
}

#pragma mark -pay 相关
- (void)createPingPPPayment:(NSDictionary *)charge {
    [Pingpp createPayment:charge appURLScheme:@"moneyexchange"  withCompletion:^(NSString *result, PingppError *error) {
        NSNumber *payresult = @0;//表示最终支付结果
        if ([result isEqualToString:@"success"]) {
            [self.view makeToast:@"支付成功！"];
            payresult = @0;
            
        } else {
            [self.view makeToast:@"支付失败！"];
            NSLog(@"Error: code=%lu msg=%@", error.code, [error getMsg]);
            payresult = @1;
        }
        //3.client将结果发给服务器
        [self handlePayResult:payresult withPayOrder:mpayorder withConfirmation:nil];
    }];
}

- (void)createPaypalConfig {
    _payPalConfiguration = [[PayPalConfiguration alloc] init];
    _payPalConfiguration.acceptCreditCards = NO;
//    _payPalConfiguration.payPalShippingAddressOption = PayPalShippingAddressOptionPayPal;
    _payPalConfiguration.merchantName = @"qukuailian";//公司名称
}

- (void)createPaypalPayment:(NSDictionary *)charge{
   
    PayPalPayment *payment = [[PayPalPayment alloc] init];
    NSNumber *money = [charge objectForKey:tag_paycost];
    NSNumber *paytype = [charge objectForKey:tag_paystype];
    NSString *currencyCode = paytype.integerValue == 1 ? @"USD" : @"CNY";
    payment.amount = [[NSDecimalNumber alloc] initWithString:[NSString stringWithFormat:@"%0.2f",money.floatValue]];
    payment.currencyCode = currencyCode;
    payment.shortDescription = @"兑换";
    payment.items = nil;
    payment.paymentDetails = nil;
    payment.intent = PayPalPaymentIntentSale;
    if (!payment.processable) {
        NSLog(@"-------PayPalPayment error------");
    }
    PayPalPaymentViewController *paymentViewController;
    paymentViewController = [[PayPalPaymentViewController alloc] initWithPayment:payment
                                                                   configuration:self.payPalConfiguration
                                                                        delegate:self];
    
    [self presentViewController:paymentViewController animated:YES completion:nil];
}

- (void)handlePayResult:(NSNumber *)payresult withPayOrder:(NSNumber *)payorder withConfirmation:(NSDictionary *)confirmation {
    NSMutableDictionary *params = [[NSMutableDictionary alloc]init];
    [params setObject:method_sel_orderpay forKey:method_sel];
    [params setObject:payorder forKey:tag_payorder];
    [params setObject:payresult forKey:tag_stype];
    if (confirmation) {
        NSDictionary *response = [confirmation objectForKey:@"response"];
        NSString *payid = [response objectForKey:@"id"];
        [params setObject:payid forKey:tag_confirmation];
    }
    [[QKLServerClient sharedNetworking] getWithUrl:server_api_default params:[QKLUtils completeUserToken:params] success:^(id response) {
        if ([QKLUtils getResponseCode:response] == 0) {
            NSDictionary *data = [response objectForKey:tag_data];
            if (data) {
                NSLog(@"%@",data);
            }
            if (payresult.integerValue == 0) {
                dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(1.5 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
                    [self.navigationController popToRootViewControllerAnimated:YES];
                });
            }
            
        } else {
            [self.view makeToast:[QKLUtils getResponseMsg:response]];
        }
    } fail:^(NSError *error) {
        [self.view makeToast:lang_net_err];
    } showHUD:self.view];
}
@end
