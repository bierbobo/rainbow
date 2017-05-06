__author__ = 'lifubo'

import os
import sys

from common.dateUtil import get_format_today
from common import data_process

class ReportMainDetailProcessor(data_process.DataProcess):

    __sqlHeader = """
        set  __sqlHeader

    """


    def __init__(self,run_date):
        super(ReportMainDetailProcessor,self).__init__(run_date)
        self.process_action = [
            {
                'action_name':'create_process_table',
                },
            {
                'action_name':'insert_into_dc_table',
                'dimensions':[
                    {
                        'partitioned_type':'dc',
                        'stock_type_where':'',
                        'stock_type':'dc',
                        'agg_field':'delv_center_num',
                        'stock_group_by':'',
                        'is_zx_exp':'zx_stock',
                        'dc_agg_field':'if(delv_center_num = 682 and target_delv_center_num is not null,target_delv_center_num,delv_center_num)'
                    },
                    {
                        'partitioned_type':'rdc',
                        'stock_type_where':'and store_type = 0 and org_dc_id is not null',
                        'stock_type':'rdc',
                        'agg_field':'org_dc_id',
                        'stock_group_by':'',
                        'is_zx_exp':'zx_stock',
                        'dc_agg_field':'org_dc_id'
                    },
                    {
                        'partitioned_type':'dc_nw',
                        'stock_type_where':'',
                        'stock_type':'dc',
                        'agg_field':'-1',
                        'stock_group_by':'group by item_sku_id ',
                        'is_zx_exp':'sum(zx_stock)',
                        'dc_agg_field':'-1'
                    },
                    {
                        'partitioned_type':'rdc_nw',
                        'stock_type_where':'and store_type = 0  and org_dc_id is not null ',
                        'stock_type':'rdc',
                        'agg_field':'-1',
                        'stock_group_by':'group by item_sku_id ',
                        'is_zx_exp':'sum(zx_stock)',
                        'dc_agg_field':'-1'
                    }
                ]
            }

        ]

        self.create_process_table =  """

        use app;
        create table if not exists app.app_dashboard_report_sku_dc_main_detail()
        """



        self.insert_into_dc_table = self.__sqlHeader + """
        insert overwrite table app.app_dashboard_report_sku_dc_main_detail
        """

def main():
    if len(sys.argv) >= 3:
        run_date = sys.argv[2]
    else:
        run_date = get_format_today('%Y-%m-%d')
    pi = ReportMainDetailProcessor(run_date)
    pi.do_job()

if __name__ == "__main__":
    main()
