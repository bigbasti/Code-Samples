using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using log4net;

namespace log4net_demo.Controllers
{
    public class HomeController : Controller
    {
        //
        // GET: /Home/

        public ActionResult Index()
        {
            ILog logger = LogManager.GetLogger(typeof(HomeController));

            logger.Info("Index Action wurde aufgerufen.....");

            try
            {
                logger.Debug("Don't mess with math dude...");
                int zero = 0;
                int endOfWorld = 12 / zero;
            }
            catch (Exception ex)
            {
                logger.Error("Ohh noez, you're dead now!", ex);
            }

            return View();
        }

    }
}
