__author__ = 'lifubo'

import sys
from common import data_process
from common.dateUtil import get_format_today

class ReportSkuDetailProcessor(data_process.DataProcess):
    def __init__(self,run_date):
        super(ReportSkuDetailProcessor,self).__init__(run_date)
        self.process_action = [
            {
                'action_name':'insert_into_table'
            }
        ]

        self.insert_into_table =  """
            ----     insert_into_table          ----
        """

    def __validate_attr(self):
        """"""
        print('validate_attr')
        pass


def main():
    if len(sys.argv) >= 3:
        run_date = sys.argv[2]
    else:
        run_date = get_format_today('%Y-%m-%d')
    pi = ReportSkuDetailProcessor(run_date)
    # pi.__validate_attr()
    pi.do_job()

if __name__ == "__main__":
    main()
