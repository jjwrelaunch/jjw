
package de.jjw.webapp.action.admin;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import de.jjw.model.duo.DuoTeam;
import de.jjw.model.fighting.Fighter;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.duo.DuoTeamManager;
import de.jjw.service.duo.FriendlyDuoFightManager;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.fighting.FighterManager;
import de.jjw.service.fighting.FriendlyFightManager;
import de.jjw.service.modelWeb.DuoFightWeb;
import de.jjw.service.modelWeb.DuoTeamWeb;
import de.jjw.service.modelWeb.FightWeb;
import de.jjw.service.modelWeb.FighterWeb;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.WebExchangeContextHelper;
import de.jjw.webapp.action.BasePage;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.constants.admin.IFighterConstants;
import de.jjw.webapp.pdf.fighting.LogListPDF;
import de.jjw.webapp.util.ConfigMain;

public class ShowFriendlyFighterAction
    extends BasePage
    implements Serializable, IGlobalWebConstants, IFighterConstants
{

    protected UIData dataTable2;

    private static String ALL_FIGHTER = "allFighter";

    private static String ALL_DUOTEAMS = "allDuoteams";

    private static String FRIENDLY_FIGHTS = "friendlyFights";

    private static String FRIENDLY_DUO_FIGHTS = "friendlyDuoFights";

    FighterManager fighterManager = null;

    DuoTeamManager duoTeamManager = null;

    FriendlyFightManager friendlyFightManager = null;

    FriendlyDuoFightManager friendlyDuoFightManager = null;

    List<FighterWeb> fighters = null;

    List<DuoTeamWeb> duoteams = null;

    Fighter fighter = null;

    DuoTeam duoteam = null;

    List<FightWeb> friendlyfights = null;
    
    List<DuoFightWeb> friendlyDuofights = null;

    public Fighter getFighter()
    {
        if ( fighter == null )
        {
            if ( getRequest().getAttribute( WEB_ADMIN_FIGHTER_FRIENDLY ) != null )
            {
                // came from all fightersite
                fighter = (Fighter) getRequest().getAttribute( WEB_ADMIN_FIGHTER_FRIENDLY );
            }
        }
        return fighter;
    }

    public List<FighterWeb> getFighters()
    {
        if ( fighterManager == null )
        {
            log.warn( "keine fighterManager Injection" );
        }
        try
        {
            if ( getRequest().getAttribute( ALL_FIGHTER ) == null )
            {
                fighters = fighterManager.getAllRegistratedFighters( getLocale() );

                getRequest().setAttribute( ALL_FIGHTER, fighters );
            }
            else
                fighters = (List<FighterWeb>) getRequest().getAttribute( ALL_FIGHTER );
        }
        catch ( JJWManagerException e )
        {
            log.error( e );
            Vector<ErrorElement> errors = new Vector<ErrorElement>();
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
        }
        return fighters;
    }

    public DuoTeam getDuoTeam()
    {
        if ( duoteam == null )
        {
            if ( getRequest().getAttribute( WEB_ADMIN_DUOTEAM_FRIENDLY ) != null )
            {
                // came from all fightersite
                duoteam = (DuoTeam) getRequest().getAttribute( WEB_ADMIN_DUOTEAM_FRIENDLY );
            }
        }
        return duoteam;
    }

    public List<DuoTeamWeb> getDuoTeams()
    {
        if ( duoTeamManager == null )
        {
            log.warn( "keine duoTeamManager Injection" );
        }
        try
        {
            if ( getRequest().getAttribute( ALL_DUOTEAMS ) == null )
            {
                duoteams = duoTeamManager.getAllRegistratedDuoTeams( getLocale() );

                getRequest().setAttribute( ALL_DUOTEAMS, duoteams );
            }
            else
                duoteams = (List<DuoTeamWeb>) getRequest().getAttribute( ALL_DUOTEAMS );
        }
        catch ( JJWManagerException e )
        {
            log.error( e );
            Vector<ErrorElement> errors = new Vector<ErrorElement>();
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
        }
        return duoteams;
    }


    public String findOpponent()
    {
        getRequest().setAttribute( WEB_ADMIN_FIGHTER_FRIENDLY, dataTable.getRowData() );
        return "findOpponent";
    }

    public String findDuoOpponent()
    {
        getRequest().setAttribute( WEB_ADMIN_DUOTEAM_FRIENDLY, dataTable2.getRowData() );
        return "findDuoOpponent";
    }

    public String submitOpponent()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        Map requestMap = context.getExternalContext().getRequestParameterMap();
        Long fighterIdRed = Long.valueOf( (String) requestMap.get( "id" ) );
        Fighter fighterBlue = (Fighter) dataTable.getRowData();
        try
        {
            if ( fighterIdRed.equals( fighterBlue.getId() ) )
            {
                fighter = new Fighter();
                fighter.setId( fighterIdRed );
                throw new JJWManagerException();
            }
            friendlyFightManager.createFriendlyFight( new ServiceExchangeContext(
                                                                                  WebExchangeContextHelper.getWebExchangeContext( getSession() ).getUserId() ),
                                                      fighterIdRed, fighterBlue.getId() );
        }
        catch ( JJWManagerException e )
        {
            log.error( e );
            Vector<ErrorElement> errors = new Vector<ErrorElement>();
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
            return null;
        }
        return "friendlies";
    }

    public String submitDuoOpponent()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        Map requestMap = context.getExternalContext().getRequestParameterMap();
        Long teamIdRed = Long.valueOf( (String) requestMap.get( "id" ) );
        DuoTeam teamBlue = (DuoTeam) dataTable2.getRowData();
        try
        {
            if ( teamIdRed.equals( teamBlue.getId() ) )
            {
                duoteam = new DuoTeam();
                duoteam.setId( teamIdRed );
                throw new JJWManagerException();
            }
            friendlyDuoFightManager.createFriendlyDuoFight( new ServiceExchangeContext(
                                                                                        WebExchangeContextHelper.getWebExchangeContext( getSession() ).getUserId() ),
                                                            teamIdRed, teamBlue.getId() );
        }
        catch ( JJWManagerException e )
        {
            log.error( e );
            Vector<ErrorElement> errors = new Vector<ErrorElement>();
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
            return null;
        }
        return "friendlies";
    }

    /**
     * @return the friendlyfights
     */
    public List<FightWeb> getFriendlyfights()
    {
        if ( friendlyFightManager == null )
        {
            log.warn( "keine friendlyFightManager Injection" );
        }
        try
        {
            if ( getRequest().getAttribute( FRIENDLY_FIGHTS ) == null )
            {
                friendlyfights = friendlyFightManager.getAllFriendlyFights();

                getRequest().setAttribute( FRIENDLY_FIGHTS, friendlyfights );
            }
            else
                friendlyfights = (List<FightWeb>) getRequest().getAttribute( FRIENDLY_FIGHTS );
        }
        catch ( JJWManagerException e )
        {
            log.error( e );
            Vector<ErrorElement> errors = new Vector<ErrorElement>();
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
        }
        return friendlyfights;
    }

    public List<DuoFightWeb> getFriendlyDuofights()
    {
        if ( friendlyDuoFightManager == null )
        {
            log.warn( "keine friendlyDuoFightManager Injection" );
        }
        try
        {
            if ( getRequest().getAttribute( FRIENDLY_DUO_FIGHTS ) == null )
            {
                friendlyDuofights = friendlyDuoFightManager.getAllFriendlyDuoFights();

                getRequest().setAttribute( FRIENDLY_DUO_FIGHTS, friendlyDuofights );
            }
            else
                friendlyDuofights = (List<DuoFightWeb>) getRequest().getAttribute( FRIENDLY_DUO_FIGHTS );
        }
        catch ( JJWManagerException e )
        {
            log.error( e );
            Vector<ErrorElement> errors = new Vector<ErrorElement>();
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
        }
        return friendlyDuofights;
    }

    public String showFight()
    {

        String contextPath = getFacesContext().getExternalContext().getRequestContextPath();

        StringBuffer sb = new StringBuffer( 120 );
        sb.append( contextPath );
        if ( getFacesContext().getExternalContext().getUserPrincipal() != null )
        {
            sb.append( SHOW_FIGHTMASK_FRIENDLY_URL );
        }

        sb.append( HTML_QUESTION_MARK ).append( HTML_PARAM_FIGHT ).append( HTML_EQUAL ).append( String.valueOf( ( (FightWeb) dataTable.getRowData() ).getId() ) );
        try
        {
            getFacesContext().getExternalContext().redirect( getFacesContext().getExternalContext().encodeActionURL( sb.toString() ) );
            return null;
        }
        catch ( IOException e1 )
        {
            log.error( "ShowFriendlyFighterAction", e1 );
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }

        return null;
    }

    public String showDuoFight()
    {

        String contextPath = getFacesContext().getExternalContext().getRequestContextPath();

        StringBuffer sb = new StringBuffer( 120 );
        sb.append( contextPath );
        if ( getFacesContext().getExternalContext().getUserPrincipal() != null )
        {
            sb.append( SHOW_DUO_FIGHTMASK_FRIENDLY_URL );
        }

        sb.append( HTML_QUESTION_MARK ).append( HTML_PARAM_FIGHT ).append( HTML_EQUAL ).append( String.valueOf( ( (DuoFightWeb) dataTable2.getRowData() ).getId() ) );
        try
        {
            getFacesContext().getExternalContext().redirect( getFacesContext().getExternalContext().encodeActionURL( sb.toString() ) );
            return null;
        }
        catch ( IOException e1 )
        {
            log.error( "ShowFriendlyFighterAction", e1 );
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }

        return null;
    }

    public String gotoNewFight()
    {
        return "friendlyFightingFight";
    }

    public String gotoNewDuoFight()
    {
        return "friendlyDuoFight";
    }

    public String deleteFight()
    {
        String ret = null;
        try
        {
            friendlyFightManager.removeFriendlyFight( ( (FightWeb) dataTable.getRowData() ).getId() );
            ret = "friendlies";
        }
        catch ( JJWManagerException e )
        {
            log.error( e );
            Vector<ErrorElement> errors = new Vector<ErrorElement>();
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
        }
        return ret;
    }

    public String deleteDuoFight()
    {
        String ret = null;
        try
        {
            friendlyDuoFightManager.removeFriendlyDuoFight( ( (DuoFightWeb) dataTable2.getRowData() ).getId() );
            ret = "friendlies";
        }
        catch ( JJWManagerException e )
        {
            log.error( e );
            Vector<ErrorElement> errors = new Vector<ErrorElement>();
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
        }
        return ret;
    }

    public String showLogListFighting()
    {

        HttpServletResponse response = (HttpServletResponse) getFacesContext().getExternalContext().getResponse();

        response.setHeader( "Expires", "0" );
        response.setHeader( "Cache-Control", "must-revalidate, post-check=0, pre-check=0" );
        response.setHeader( "Pragma", "public" );
        response.setContentType( "application/pdf" );
        try
        {
            new LogListPDF( "de.jjw.webapp.messages.admin.fightingclass",
                            ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine1(), response,
                            friendlyFightManager.getFight( ( (FightWeb) dataTable.getRowData() ).getId() ) );
            getFacesContext().responseComplete();
        }
        catch ( JJWManagerException e )
        {
            log.error( e );
            Vector<ErrorElement> errors = new Vector<ErrorElement>();
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
        }
        catch ( Exception e )
        {
            log.error( e );
            Vector<ErrorElement> errors = new Vector<ErrorElement>();
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
        }
        return null;

    }

    public String showLogListDuo()
    {

        HttpServletResponse response = (HttpServletResponse) getFacesContext().getExternalContext().getResponse();

        response.setHeader( "Expires", "0" );
        response.setHeader( "Cache-Control", "must-revalidate, post-check=0, pre-check=0" );
        response.setHeader( "Pragma", "public" );
        response.setContentType( "application/pdf" );
        try
        {
        new de.jjw.webapp.pdf.duo.LogListPDF( "de.jjw.webapp.messages.admin.duoclass",
                                              ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine1(),
                                                  response,
                                                  friendlyDuoFightManager.getDuoFight( ( (DuoFightWeb) dataTable2.getRowData() ).getId() ) );

        getFacesContext().responseComplete();
        }
        catch ( JJWManagerException e )
        {
            log.error( e );
            Vector<ErrorElement> errors = new Vector<ErrorElement>();
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
        }
        catch ( Exception e )
        {
            log.error( e );
            Vector<ErrorElement> errors = new Vector<ErrorElement>();
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
        }
        return null;
    }

    /**
     * @param fighterManager The fighterManager to set.
     */
    public void setFighterManager( FighterManager fighterManager )
    {
        this.fighterManager = fighterManager;
    }

    /**
     * @param friendlyFightManager the friendlyFightManager to set
     */
    public void setFriendlyFightManager( FriendlyFightManager friendlyFightManager )
    {
        this.friendlyFightManager = friendlyFightManager;
    }

    /**
     * @param duoTeamManager the duoTeamManager to set
     */
    public void setDuoTeamManager( DuoTeamManager duoTeamManager )
    {
        this.duoTeamManager = duoTeamManager;
    }

    /**
     * @param friendlyDuoFightManager the friendlyDuoFightManager to set
     */
    public void setFriendlyDuoFightManager( FriendlyDuoFightManager friendlyDuoFightManager )
    {
        this.friendlyDuoFightManager = friendlyDuoFightManager;
    }

    /**
     * @return the dataTable2
     */
    public UIData getDataTable2()
    {
        return dataTable2;
    }

    /**
     * @param dataTable2 the dataTable2 to set
     */
    public void setDataTable2( UIData dataTable2 )
    {
        this.dataTable2 = dataTable2;
    }

}
