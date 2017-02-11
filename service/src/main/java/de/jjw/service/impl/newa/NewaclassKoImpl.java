/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaclassKoImpl.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:42
 *
 * Ju Jutsu Web is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Ju Jutsu Web is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Ju Jutsu Web.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.jjw.service.impl.newa;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.dao.newa.NewaFightDao;
import de.jjw.model.newa.NewaFight;
import de.jjw.model.newa.NewaFighter;
import de.jjw.model.newa.NewaKoClass;
import de.jjw.service.impl.KoImplAbstract;
import de.jjw.service.impl.SolaceDTO;
import de.jjw.service.mapper.newa.NewaFightMapper;
import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;

public class NewaclassKoImpl
    extends KoImplAbstract
    implements IValueConstants
{


    public NewaKoClass doKo( NewaKoClass fc, NewaFightDao fightDao )
        throws JJWDataLayerException
    {

        doMainRoundDummyFights( fc );

        List<NewaFight> changeFights = new ArrayList<NewaFight>();
        int numberOfFight = fc.getFightListMapPoolA().size() - 1;
        NewaFight fight = null;
        for (; numberOfFight >= 0; numberOfFight-- )
        {
            fight = fc.getFightListMapPoolA().get( Integer.valueOf( numberOfFight ) );

            if (
                ( fight.getFighterIdRed() == fight.getWinnerId().longValue() && fight.getFighterIdRed() != LONG_MIN ) ||
                    fight.getWinnerId().longValue() == LONG_MIN && fight.getFighterIdBlue() == LONG_DEFAULT &&
                        fight.getFighterIdRed() != LONG_DEFAULT )
            {
                // test, ob weiter gesetzt
                if ( numberOfFight > 0 && numberOfFight % 2 == 0 && fight.getFighterIdRed() !=
                    fc.getFightListMapPoolA().get( Integer.valueOf( ( numberOfFight - 1 ) / 2 ) ).getFighterIdBlue() )
                {
                    //update Fight, clear Inputs and overwrite
                    changeFights.add(
                        updateFight( fc.getFightListMapPoolA(), numberOfFight, FIGHTER_RED, fight.getFighterRed() ) );
                }

                if ( numberOfFight > 0 && numberOfFight % 2 != 0 && fight.getFighterIdRed() !=
                    fc.getFightListMapPoolA().get( Integer.valueOf( ( numberOfFight - 1 ) / 2 ) ).getFighterIdRed() )
                {

                    changeFights.add(
                        updateFight( fc.getFightListMapPoolA(), numberOfFight, FIGHTER_BLUE, fight.getFighterRed() ) );
                }
                
                if(numberOfFight == 0){
                    
                    fight.setWinnerId( fight.getFighterIdRed() );                   
                    changeFights.add( fight );
                }
                changeFights.add( updateSolaceRound( fc, fight, numberOfFight, true ) );
            }

            // when firstRound and no fighter then set free fight in solace Round
            if ( fight.getFighterIdBlue() == LONG_MIN && fight.getFighterIdRed() == LONG_MIN &&
                isFirstRound( numberOfFight, fc.getFighterListPoolA().size() + fc.getFighterListPoolA().size() ) )
            {
                updateSolaceRound( fc, fight, numberOfFight, true );
                continue;
            }
            // blue wins
            if ( ( fight.getFighterIdBlue() == fight.getWinnerId().longValue() &&
                fight.getFighterIdBlue() != LONG_MIN ) ||
                fight.getWinnerId().longValue() == LONG_MIN && fight.getFighterIdBlue() != LONG_DEFAULT &&
                    fight.getFighterIdRed() == LONG_DEFAULT )
            {
                // test, ob weiter gesetzt
                if ( numberOfFight > 0 && numberOfFight % 2 == 0 && fight.getFighterIdBlue() !=
                    fc.getFightListMapPoolA().get( Integer.valueOf( ( numberOfFight - 1 ) / 2 ) ).getFighterIdBlue() )
                {

                    changeFights.add(
                        updateFight( fc.getFightListMapPoolA(), numberOfFight, FIGHTER_RED, fight.getFighterBlue() ) );
                }

                if ( numberOfFight > 0 && numberOfFight % 2 != 0 && fight.getFighterIdBlue() !=
                    fc.getFightListMapPoolA().get( Integer.valueOf( ( numberOfFight - 1 ) / 2 ) ).getFighterIdRed() )
                {
                    //update Fight, clear Inputs and overwrite
                    changeFights.add(
                        updateFight( fc.getFightListMapPoolA(), numberOfFight, FIGHTER_BLUE, fight.getFighterBlue() ) );
                }
                
                if(numberOfFight == 0){
                    
                    fight.setWinnerId( fight.getFighterIdBlue() );                
                    changeFights.add( fight );
                }
                changeFights.add( updateSolaceRound( fc, fight, numberOfFight, true ) );

            }

            // dq
            if ( fight.getWinnerId().longValue() == LONG_DEFAULT )
            {
                if ( numberOfFight > 0 && numberOfFight % 2 != 0 && LONG_DEFAULT != fight.getFighterIdRed() )
                {

                    changeFights.add( updateFight( fc.getFightListMapPoolA(), numberOfFight, FIGHTER_BLUE, null ) );
                }

                if ( numberOfFight > 0 && numberOfFight % 2 == 0 && LONG_DEFAULT != fight.getFighterIdBlue() )
                {

                    changeFights.add( updateFight( fc.getFightListMapPoolA(), numberOfFight, FIGHTER_RED, null ) );
                }
                changeFights.add( updateSolaceRound( fc, fight, numberOfFight, true ) );
            }

            // free fight
            if ( fight.getWinnerId().longValue() == LONG_MIN && fight.getFighterIdBlue() == LONG_DEFAULT &&
                fight.getFighterIdRed() == LONG_DEFAULT )
            {

//				if (fight.getFighterIdBlue() == LONG_DEFAULT){
//					if(numberOfFight %2 == 0 && fight.getFighterIdRed() != 
//						fc.getFightListMapPoolA().get(Integer.valueOf((numberOfFight-1)/2)).getFighterIdBlue()){
//						//update Fight, clear Inputs and overwrite
//						changeFights.add(updateFight(fc.getFightListMapPoolA(), numberOfFight, FIGHTER_RED, fight.getFighterRed()));					
//						changeFights.add(updateSolaceRound(fc, fight, numberOfFight, true));
//						}
//				}
//				
//				if (fight.getFighterIdRed() == LONG_DEFAULT){
//					
//				}

            }
        }

        numberOfFight = fc.getFightListMapPoolB().size() - 1;
        fight = null;
        for (; numberOfFight >= 0; numberOfFight-- )
        {
            fight = fc.getFightListMapPoolB().get( Integer.valueOf( numberOfFight ) );

            if (
                ( fight.getFighterIdRed() == fight.getWinnerId().longValue() && fight.getFighterIdRed() != LONG_MIN ) ||
                    fight.getWinnerId().longValue() == LONG_MIN && fight.getFighterIdBlue() == LONG_DEFAULT &&
                        fight.getFighterIdRed() != LONG_DEFAULT )
            {
                // test, ob weiter gesetzt
                if ( numberOfFight > 0 && numberOfFight % 2 == 0 && fight.getFighterIdRed() !=
                    fc.getFightListMapPoolB().get( Integer.valueOf( ( numberOfFight - 1 ) / 2 ) ).getFighterIdBlue() )
                {
                    //update Fight, clear Inputs and overwrite
                    changeFights.add(
                        updateFight( fc.getFightListMapPoolB(), numberOfFight, FIGHTER_RED, fight.getFighterRed() ) );
                }

                if ( numberOfFight > 0 && numberOfFight % 2 != 0 && fight.getFighterIdRed() !=
                    fc.getFightListMapPoolB().get( Integer.valueOf( ( numberOfFight - 1 ) / 2 ) ).getFighterIdRed() )
                {

                    changeFights.add(
                        updateFight( fc.getFightListMapPoolB(), numberOfFight, FIGHTER_BLUE, fight.getFighterRed() ) );
                }
                if(numberOfFight == 0){
                    
                    fight.setWinnerId( fight.getFighterIdRed() );                    
                    changeFights.add( fight );
                }
                changeFights.add( updateSolaceRound( fc, fight, numberOfFight, false ) );
            }

            // when firstRound and no fighter then set free fight in solace Round
            if ( fight.getFighterIdBlue() == LONG_MIN && fight.getFighterIdRed() == LONG_MIN &&
                isFirstRound( numberOfFight, fc.getFighterListPoolB().size() + fc.getFighterListPoolB().size() ) )
            {
                updateSolaceRound( fc, fight, numberOfFight, false );
                continue;
            }

            if ( ( fight.getFighterIdBlue() == fight.getWinnerId().longValue() &&
                fight.getFighterIdBlue() != LONG_MIN ) ||
                fight.getWinnerId().longValue() == LONG_MIN && fight.getFighterIdBlue() != LONG_DEFAULT &&
                    fight.getFighterIdRed() == LONG_DEFAULT )
            {
                // test, ob weiter gesetzt
                if ( numberOfFight > 0 && numberOfFight % 2 == 0 && fight.getFighterIdBlue() !=
                    fc.getFightListMapPoolB().get( Integer.valueOf( ( numberOfFight - 1 ) / 2 ) ).getFighterIdBlue() )
                {

                    changeFights.add(
                        updateFight( fc.getFightListMapPoolB(), numberOfFight, FIGHTER_RED, fight.getFighterBlue() ) );
                }

                if ( numberOfFight > 0 && numberOfFight % 2 != 0 && fight.getFighterIdBlue() !=
                    fc.getFightListMapPoolB().get( Integer.valueOf( ( numberOfFight - 1 ) / 2 ) ).getFighterIdRed() )
                {
                    //update Fight, clear Inputs and overwrite
                    changeFights.add(
                        updateFight( fc.getFightListMapPoolB(), numberOfFight, FIGHTER_BLUE, fight.getFighterBlue() ) );
                }
                if(numberOfFight == 0){
                    
                    fight.setWinnerId( fight.getFighterIdBlue() );
                    changeFights.add( fight );
                }
                changeFights.add( updateSolaceRound( fc, fight, numberOfFight, false ) );

            }

            // dq
            if ( fight.getWinnerId().longValue() == LONG_DEFAULT )
            {
                if ( numberOfFight > 0 && numberOfFight % 2 != 0 && LONG_DEFAULT != fight.getFighterIdRed() )
                {

                    changeFights.add( updateFight( fc.getFightListMapPoolB(), numberOfFight, FIGHTER_BLUE, null ) );
                }

                if ( numberOfFight > 0 && numberOfFight % 2 == 0 && LONG_DEFAULT != fight.getFighterIdBlue() )
                {

                    changeFights.add( updateFight( fc.getFightListMapPoolB(), numberOfFight, FIGHTER_RED, null ) );
                }
                changeFights.add( updateSolaceRound( fc, fight, numberOfFight, false ) );
            }

        }

        //handleFreeLoserRound()
        handleLoserRoundA( fc, changeFights );
        handleLoserRoundB( fc, changeFights );
        handle3rdPlaceFights(fc, changeFights, fightDao);

        //handle final fight
        changeFights.add( handleFinal( fc ) );

        for ( int i = 0; i < fc.getFightListLooserMapPoolA().size(); i++ )
        {
            if ( fc.getFightListLooserMapPoolA().get( Integer.valueOf( i ) ).isDirty() &&
                fc.getFightListLooserMapPoolA().get( Integer.valueOf( i ) ).getId() != null )
            {
                fightDao.saveFight( fc.getFightListLooserMapPoolA().get( Integer.valueOf( i ) ) );
            }
        }

        for ( int i = 0; i < fc.getFightListLooserMapPoolB().size(); i++ )
        {
            if ( fc.getFightListLooserMapPoolB().get( Integer.valueOf( i ) ).isDirty() &&
                fc.getFightListLooserMapPoolB().get( Integer.valueOf( i ) ).getId() != null )
            {
                fightDao.saveFight( fc.getFightListLooserMapPoolB().get( Integer.valueOf( i ) ) );
            }
        }

        for ( NewaFight changedFight : changeFights )
        {
            if ( changedFight != null && changedFight.getId() != null )
            //FightMapper.mapFightToDB(FightMapper.mapFightFromDB(changedFight), fightDao.getFight(changedFight.getId()), false);
            {

                if ( fight.getWinnerId() < TypeUtil.LONG_DEFAULT )
                {
                    changedFight.setModificationId( LONG_1 );
                    changedFight.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
                }
                fightDao.saveFight( changedFight );
            }
        }
        return fc;
    }

    /**
     * @param fc
     */
    private void doMainRoundDummyFights( NewaKoClass fc )
    {
        // create empty dummy fights, which are not stored in db
        int mainRoundFights =
            NewaclassKoCreateImpl.getNumberOfFightsInMainRound(
            fc.getFighterListPoolA().size() + fc.getFighterListPoolB().size() ) / 2;
        int poolsize =
            NewaclassKoCreateImpl.getPoolsize( fc.getFighterListPoolA().size() + fc.getFighterListPoolB().size() );
        NewaFight fight = null;
        for ( int i = 0; i < mainRoundFights; i++ )
        {
            if ( fc.getFightListMapPoolA().get( Integer.valueOf( i ) ) == null )
            {
                fight = new NewaFight();
                setTechnicalDBFieldsForDummyFight( fight );
                fight.setId( TypeUtil.LONG_OBJECT_EMPTY );
                // set the fighter in the dummy fight, which had a free fight
                if ( i >= ( poolsize / 4 - 1 ) )
                {
                    if ( i % 2 != 0 )
                    {
                        fight.setFighterRed( fc.getFightListMapPoolA().get( ( i + 1 ) / 2 - 1 ).getFighterRed() );
                    }
                    else
                    {
                        fight.setFighterRed( fc.getFightListMapPoolA().get( ( i + 1 ) / 2 - 1 ).getFighterBlue() );
                    }
                }
                fc.getFightListMapPoolA().put( Integer.valueOf( i ), fight );
            }

            if ( fc.getFightListMapPoolB().get( Integer.valueOf( i ) ) == null )
            {
                fight = new NewaFight();
                setTechnicalDBFieldsForDummyFight( fight );
                fight.setId( TypeUtil.LONG_OBJECT_EMPTY );
                // set the fighter in the dummy fight, which had a free fight
                if ( i >= ( poolsize / 4 - 1 ) )
                {
                    if ( i % 2 != 0 )
                    {
                        fight.setFighterRed( fc.getFightListMapPoolB().get( ( i + 1 ) / 2 - 1 ).getFighterRed() );
                    }
                    else
                    {
                        fight.setFighterRed( fc.getFightListMapPoolB().get( ( i + 1 ) / 2 - 1 ).getFighterBlue() );
                    }
                }
                fc.getFightListMapPoolB().put( Integer.valueOf( i ), fight );
            }

        }
    }


    /**
     * handles the final-fight
     *
     * @param fc
     * @return
     */
    private NewaFight handleFinal( NewaKoClass fc )
    {
        boolean isFinalUpdate = false;
        if ( fc.getFightListMapPoolA().get( 0 ).getWinnerId().longValue() != fc.getFinalFight().getFighterIdRed() )
        {

            long otherFighterId;
            NewaFighter otherFighter;

            otherFighterId = fc.getFinalFight().getFighterIdBlue();
            otherFighter = fc.getFinalFight().getFighterBlue();

            fc.getFinalFight().resetForKoList();
            fc.getFinalFight().setFighterIdBlue( otherFighterId );
            fc.getFinalFight().setFighterBlue( otherFighter );

            if ( fc.getFightListMapPoolA().get( 0 ).getWinnerId().longValue() ==
                fc.getFightListMapPoolA().get( 0 ).getFighterIdRed() )
            {
                // fighter red is in the final
                fc.getFinalFight().setFighterIdRed( fc.getFightListMapPoolA().get( 0 ).getFighterIdRed() );
                fc.getFinalFight().setFighterRed( fc.getFightListMapPoolA().get( 0 ).getFighterRed() );
            }

            if ( fc.getFightListMapPoolA().get( 0 ).getWinnerId().longValue() ==
                fc.getFightListMapPoolA().get( 0 ).getFighterIdBlue() )
            {
                // fighter blue is in the final
                fc.getFinalFight().setFighterIdRed( fc.getFightListMapPoolA().get( 0 ).getFighterIdBlue() );
                fc.getFinalFight().setFighterRed( fc.getFightListMapPoolA().get( 0 ).getFighterBlue() );
            }

            if ( fc.getFightListMapPoolA().get( 0 ).getWinnerId().longValue() == LONG_DEFAULT )
            {
                // no fighter in the final
                fc.getFinalFight().setFighterIdRed( LONG_DEFAULT );
                fc.getFinalFight().setFighterRed( null );

            }
            isFinalUpdate = true;
        }

        if ( fc.getFightListMapPoolB().get( 0 ).getWinnerId().longValue() != fc.getFinalFight().getFighterIdBlue() )
        {

            long otherFighterId;
            NewaFighter otherFighter;

            otherFighterId = fc.getFinalFight().getFighterIdRed();
            otherFighter = fc.getFinalFight().getFighterRed();

            fc.getFinalFight().resetForKoList();
            fc.getFinalFight().setFighterIdRed( otherFighterId );
            fc.getFinalFight().setFighterRed( otherFighter );

            if ( fc.getFightListMapPoolB().get( 0 ).getWinnerId().longValue() ==
                fc.getFightListMapPoolB().get( 0 ).getFighterIdRed() )
            {
                // fighter red is in the final
                fc.getFinalFight().setFighterIdBlue( fc.getFightListMapPoolB().get( 0 ).getFighterIdRed() );
                fc.getFinalFight().setFighterBlue( fc.getFightListMapPoolB().get( 0 ).getFighterRed() );
            }

            if ( fc.getFightListMapPoolB().get( 0 ).getWinnerId().longValue() ==
                fc.getFightListMapPoolB().get( 0 ).getFighterIdBlue() )
            {
                // fighter blue is in the final
                fc.getFinalFight().setFighterIdBlue( fc.getFightListMapPoolB().get( 0 ).getFighterIdBlue() );
                fc.getFinalFight().setFighterBlue( fc.getFightListMapPoolB().get( 0 ).getFighterBlue() );
            }

            if ( fc.getFightListMapPoolA().get( 0 ).getWinnerId().longValue() == LONG_DEFAULT )
            {
                // no fighter in the final
                fc.getFinalFight().setFighterIdBlue( LONG_DEFAULT );
                fc.getFinalFight().setFighterBlue( null );

            }
            isFinalUpdate = true;
        }

        if ( isFinalUpdate )
        {

            if ( fc.getFinalFight().getFighterIdRed() == LONG_DEFAULT &&
                fc.getFinalFight().getFighterIdBlue() > LONG_DEFAULT )
            {
                fc.getFinalFight().setWinnerId( fc.getFinalFight().getFighterIdBlue() );
            }

            if ( fc.getFinalFight().getFighterIdBlue() == LONG_DEFAULT &&
                fc.getFinalFight().getFighterIdRed() > LONG_DEFAULT )
            {
                fc.getFinalFight().setWinnerId( fc.getFinalFight().getFighterIdRed() );
            }
        }

        if ( isFinalUpdate )
        {
            return fc.getFinalFight();
        }
        else
        {
            return null;
        }
    }


    /**
     * handles the LoserRoundA
     *
     * @param fc
     * @param changeFights
     */
    private void handleLoserRoundA( NewaKoClass fc, List<NewaFight> changeFights )
    {
        int numberOfFight;
        NewaFight fight;
        //handle solace rounds
        numberOfFight = fc.getFightListLooserMapPoolA().size() - 1;
        for (; numberOfFight > 0; numberOfFight-- )
        {
            fight = fc.getFightListLooserMapPoolA().get( Integer.valueOf( numberOfFight ) );

            if (
                ( fight.getFighterIdRed() == fight.getWinnerId().longValue() && fight.getFighterIdRed() != LONG_MIN ) ||
                    fight.getWinnerId().longValue() == LONG_MIN && fight.getFighterIdBlue() == LONG_DEFAULT &&
                        fight.getFighterIdRed() > LONG_DEFAULT )
            {
                // test, ob weiter gesetzt
                if ( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getRedBlue() == FIGHTER_RED &&
                    fight.getFighterIdRed() != fc.getFightListLooserMapPoolA().get(
                        SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).getFighterIdRed() )
                {
                    changeFights.add( updateSolaceFight( fc.getFightListLooserMapPoolA(), numberOfFight, FIGHTER_BLUE,
                                                         fight.getFighterRed() ) );
                }
                if ( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getRedBlue() == FIGHTER_BLUE &&
                    fight.getFighterIdRed() != fc.getFightListLooserMapPoolA().get(
                        SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).getFighterIdBlue() )
                {
                    changeFights.add( updateSolaceFight( fc.getFightListLooserMapPoolA(), numberOfFight, FIGHTER_RED,
                                                         fight.getFighterRed() ) );
                }
            }

            if ( ( fight.getFighterIdBlue() == fight.getWinnerId().longValue() &&
                fight.getFighterIdBlue() != LONG_MIN ) ||
                fight.getWinnerId().longValue() == LONG_MIN && fight.getFighterIdBlue() > LONG_DEFAULT &&
                    fight.getFighterIdRed() == LONG_DEFAULT )
            {
                // test, ob weiter gesetzt
                if ( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getRedBlue() == FIGHTER_RED &&
                    fight.getFighterIdBlue() != fc.getFightListLooserMapPoolA().get(
                        SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).getFighterIdRed() )
                {
                    changeFights.add( updateSolaceFight( fc.getFightListLooserMapPoolA(), numberOfFight, FIGHTER_BLUE,
                                                         fight.getFighterBlue() ) );
                }
                if ( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getRedBlue() == FIGHTER_BLUE &&
                    fight.getFighterIdBlue() != fc.getFightListLooserMapPoolA().get(
                        SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).getFighterIdBlue() )
                {
                    changeFights.add( updateSolaceFight( fc.getFightListLooserMapPoolA(), numberOfFight, FIGHTER_RED,
                                                         fight.getFighterBlue() ) );
                }
            }

            // dq or delete next Fights when korect tree
            if ( fight.getWinnerId().longValue() == LONG_DEFAULT )
            {
                if ( numberOfFight % 2 != 0 && LONG_DEFAULT != fight.getFighterIdRed() )
                {
                    changeFights.add( updateSolaceFight( fc.getFightListLooserMapPoolA(), numberOfFight, FIGHTER_RED,
                                                         null ) );
                }
            }

            if ( fight.getWinnerId().longValue() == LONG_DEFAULT )
            {
                if ( numberOfFight % 2 == 0 && LONG_DEFAULT != fight.getFighterIdBlue() )
                {

                    changeFights.add( updateSolaceFight( fc.getFightListLooserMapPoolA(), numberOfFight, FIGHTER_BLUE,
                                                         null ) );
                }
            }

            // free fight, because of no firstround fighter
            if ( fight.getWinnerId().longValue() == LONG_MIN && fight.getFighterIdBlue() == LONG_DEFAULT &&
                fight.getFighterIdRed() == LONG_DEFAULT )
            {
                updateSolaceFight( fc.getFightListLooserMapPoolA(), numberOfFight, FIGHTER_RED, null );


            }
        }               
    }

    /**
     * handles the LoserRoundB
     * 
     * @param fc
     * @param changeFights
     */
    private void handleLoserRoundB( NewaKoClass fc, List<NewaFight> changeFights )
    {
        int numberOfFight;
        NewaFight fight;
        //handle solace rounds
        numberOfFight = fc.getFightListLooserMapPoolB().size() - 1;
        for (; numberOfFight > 0; numberOfFight-- )
        {
            fight = fc.getFightListLooserMapPoolB().get( Integer.valueOf( numberOfFight ) );

            if (
                ( fight.getFighterIdRed() == fight.getWinnerId().longValue() && fight.getFighterIdRed() != LONG_MIN ) ||
                    fight.getWinnerId().longValue() == LONG_MIN && fight.getFighterIdBlue() == LONG_DEFAULT &&
                        fight.getFighterIdRed() != LONG_DEFAULT )
            {
                // test, ob weiter gesetzt
                if ( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getRedBlue() == FIGHTER_RED &&
                    fight.getFighterIdRed() != fc.getFightListLooserMapPoolB().get(
                        SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).getFighterIdRed() )
                {
                    changeFights.add( updateSolaceFight( fc.getFightListLooserMapPoolB(), numberOfFight, FIGHTER_BLUE,
                                                         fight.getFighterRed() ) );
                }
                if ( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getRedBlue() == FIGHTER_BLUE &&
                    fight.getFighterIdRed() != fc.getFightListLooserMapPoolB().get(
                        SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).getFighterIdBlue() )
                {
                    changeFights.add( updateSolaceFight( fc.getFightListLooserMapPoolB(), numberOfFight, FIGHTER_RED,
                                                         fight.getFighterRed() ) );
                }
            }

            if ( ( fight.getFighterIdBlue() == fight.getWinnerId().longValue() &&
                fight.getFighterIdBlue() != LONG_MIN ) ||
                fight.getWinnerId().longValue() == LONG_MIN && fight.getFighterIdBlue() != LONG_DEFAULT &&
                    fight.getFighterIdRed() == LONG_DEFAULT )
            {
                // test, ob weiter gesetzt
                if ( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getRedBlue() == FIGHTER_RED &&
                    fight.getFighterIdBlue() != fc.getFightListLooserMapPoolB().get(
                        SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).getFighterIdRed() )
                {
                    changeFights.add( updateSolaceFight( fc.getFightListLooserMapPoolB(), numberOfFight, FIGHTER_BLUE,
                                                         fight.getFighterBlue() ) );
                }
                if ( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getRedBlue() == FIGHTER_BLUE &&
                    fight.getFighterIdBlue() != fc.getFightListLooserMapPoolB().get(
                        SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).getFighterIdBlue() )
                {
                    changeFights.add( updateSolaceFight( fc.getFightListLooserMapPoolB(), numberOfFight, FIGHTER_RED,
                                                         fight.getFighterBlue() ) );
                }
            }

            // dq
            if ( fight.getWinnerId().longValue() == LONG_DEFAULT )
            {
                if ( numberOfFight % 2 != 0 && LONG_DEFAULT != fight.getFighterIdRed() )
                {

                    changeFights.add( updateSolaceFight( fc.getFightListLooserMapPoolB(), numberOfFight, FIGHTER_RED,null ) );
                }
            }

            if ( fight.getWinnerId().longValue() == LONG_DEFAULT )
            {
                if ( numberOfFight % 2 == 0 && LONG_DEFAULT != fight.getFighterIdBlue() )
                {

                    changeFights.add( updateSolaceFight( fc.getFightListLooserMapPoolB(), numberOfFight, FIGHTER_BLUE, null ) );
                }
            }

            // free fight
            if ( fight.getWinnerId().longValue() == LONG_MIN && fight.getFighterIdBlue() == LONG_DEFAULT &&
                fight.getFighterIdRed() == LONG_DEFAULT )
            {
                updateSolaceFight( fc.getFightListLooserMapPoolB(), numberOfFight, FIGHTER_RED, null );

            }
        }
    }


    private NewaFight updateSolaceRound( NewaKoClass fc, NewaFight actualFight, int actualFightNumber, boolean isPoolA )
    {

        NewaFight solaceFight = null;
        int redBlue = 0;
        SolaceDTO dto = null;

        switch ( getPoolsize( fc.getFighterListPoolA().size() + fc.getFighterListPoolB().size() ) )
        {

            case SIZE_KO_16:
                dto = SOLACE_MA_16.get( Integer.valueOf( actualFightNumber ) );
                break;

            case SIZE_KO_32:
                dto = SOLACE_MA_32.get( Integer.valueOf( actualFightNumber ) );
                break;

            case SIZE_KO_64:
                dto = SOLACE_MA_64.get( Integer.valueOf( actualFightNumber ) );
                break;

        }

        if ( isPoolA && dto.isChange() )
        {
            solaceFight = fc.getFightListLooserMapPoolB().get( dto.getNumberInSolaceRound() );
            redBlue = dto.getRedBlue();
        }

        if ( !isPoolA && dto.isChange() )
        {
            solaceFight = fc.getFightListLooserMapPoolA().get( dto.getNumberInSolaceRound() );
            redBlue = dto.getRedBlue();
        }

        if ( isPoolA && !dto.isChange() )
        {
            solaceFight = fc.getFightListLooserMapPoolA().get( dto.getNumberInSolaceRound() );
            redBlue = dto.getRedBlue();
        }

        if ( !isPoolA && !dto.isChange() )
        {
            solaceFight = fc.getFightListLooserMapPoolB().get( dto.getNumberInSolaceRound() );
            redBlue = dto.getRedBlue();
        }
        // case: no one set to loserRound in first round
        if ( actualFight.getWinnerId().longValue() == TypeUtil.LONG_EMPTY &&
            actualFight.getFighterIdBlue() == TypeUtil.LONG_EMPTY &&
            actualFight.getFighterIdRed() == TypeUtil.LONG_EMPTY )
        {
            updateSolaceFight( solaceFight, redBlue, null );
            return solaceFight;
        }

        // cases: double DQ , free fight, red won, blue won
        if ( actualFight.getWinnerId().longValue() == 0 )
        {
            // double DQ
            if ( FIGHTER_RED == redBlue && solaceFight.getFighterIdRed() != 0 )
            {
                updateSolaceFight( solaceFight, redBlue, null );
            }

            if ( FIGHTER_BLUE == redBlue && solaceFight.getFighterIdBlue() != 0 )
            {
                updateSolaceFight( solaceFight, redBlue, null );
            }
        }

        if ( actualFight.getWinnerId().longValue() == actualFight.getFighterIdBlue() )
        {
            if ( FIGHTER_RED == redBlue && solaceFight.getFighterIdRed() != actualFight.getFighterIdRed() )
            {
                updateSolaceFight( solaceFight, redBlue, actualFight.getFighterRed() );
            }

            if ( FIGHTER_BLUE == redBlue && solaceFight.getFighterIdBlue() != actualFight.getFighterIdRed() )
            {
                updateSolaceFight( solaceFight, redBlue, actualFight.getFighterRed() );
            }
        }

        if ( actualFight.getWinnerId().longValue() == actualFight.getFighterIdRed() )
        {
            if ( FIGHTER_RED == redBlue && solaceFight.getFighterIdBlue() != actualFight.getFighterIdBlue() )
            {
                updateSolaceFight( solaceFight, redBlue, actualFight.getFighterBlue() );
            }

            if ( FIGHTER_BLUE == redBlue && solaceFight.getFighterIdRed() != actualFight.getFighterIdBlue() )
            {
                updateSolaceFight( solaceFight, redBlue, actualFight.getFighterBlue() );
            }
        }

        return solaceFight;

    }

    /**
     * update a fight in the solaceRound, if fighter is null so set new fightid=LONG_MIN
     * this method ist for setting the fights, because of the mainround
     *
     * @param fight
     * @param redBlue
     * @param fighter
     */
    private void updateSolaceFight( NewaFight fight, int redBlue, NewaFighter fighter )
    {
        long otherFighterId;
        NewaFighter otherFighter;

        if ( fighter == null )
        {
            if ( FIGHTER_BLUE == redBlue && fight.getFighterIdBlue() != LONG_DEFAULT )
            {
                otherFighter = fight.getFighterRed();
                otherFighterId = fight.getFighterIdRed();
                fight.resetForKoList();
                fight.setFighterIdRed( otherFighterId );
                fight.setFighterRed( otherFighter );
                fight.setFighterIdBlue( LONG_DEFAULT );
                fight.setFighterBlue( null );
            }

            if ( FIGHTER_RED == redBlue && fight.getFighterIdRed() != LONG_DEFAULT )
            {
                otherFighter = fight.getFighterBlue();
                otherFighterId = fight.getFighterIdBlue();
                fight.resetForKoList();
                fight.setFighterIdBlue( otherFighterId );
                fight.setFighterBlue( otherFighter );
                fight.setFighterIdRed( LONG_DEFAULT );
                fight.setFighterRed( null );
            }
        }
        else
        {
            if ( FIGHTER_BLUE == redBlue && fight.getFighterIdBlue() != fighter.getId().longValue() )
            {
                otherFighter = fight.getFighterRed();
                otherFighterId = fight.getFighterIdRed();
                fight.resetForKoList();
                fight.setFighterIdRed( otherFighterId );
                fight.setFighterRed( otherFighter );
                fight.setFighterIdBlue( fighter.getId().longValue() );
                fight.setFighterBlue( fighter );
            }
            if ( FIGHTER_RED == redBlue && fight.getFighterIdRed() != fighter.getId().longValue() )
            {
                otherFighter = fight.getFighterBlue();
                otherFighterId = fight.getFighterIdBlue();
                fight.resetForKoList();
                fight.setFighterIdBlue( otherFighterId );
                fight.setFighterBlue( otherFighter );
                fight.setFighterIdRed( fighter.getId().longValue() );
                fight.setFighterRed( fighter );
            }
        }

        if ( fight.getWinnerId() < TypeUtil.LONG_DEFAULT )
        {
            fight.setModificationId( LONG_1 );
            fight.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
        }
    }


    /**
     * update the next round Fight
     *
     * @param fightMap
     * @param numberOfFight
     * @param redBlue
     * @param fighter
     * @return
     */
    private NewaFight updateFight( Map<Integer, NewaFight> fightMap, int numberOfFight, int redBlue, NewaFighter fighter )
    {
        long otherFighterId;
        int nextFightNumber = ( numberOfFight - 1 ) / 2;
        NewaFighter otherFighter;
        if ( FIGHTER_BLUE == redBlue )
        {
            otherFighterId = fightMap.get( Integer.valueOf( nextFightNumber ) ).getFighterIdBlue();
            otherFighter = fightMap.get( Integer.valueOf( nextFightNumber ) ).getFighterBlue();

            fightMap.get( Integer.valueOf( nextFightNumber ) ).resetForKoList();
            fightMap.get( Integer.valueOf( nextFightNumber ) ).setFighterIdBlue( otherFighterId );
            fightMap.get( Integer.valueOf( nextFightNumber ) ).setFighterBlue( otherFighter );

            if ( fighter != null )
            {
                fightMap.get( Integer.valueOf( nextFightNumber ) ).setFighterIdRed( fighter.getId() );
                fightMap.get( Integer.valueOf( nextFightNumber ) ).setFighterRed( fighter );
            }
            else
            {
                fightMap.get( Integer.valueOf( nextFightNumber ) ).setFighterIdRed( LONG_DEFAULT );
                fightMap.get( Integer.valueOf( nextFightNumber ) ).setFighterRed( null );
            }

        }

        if ( FIGHTER_RED == redBlue )
        {
            otherFighterId = fightMap.get( Integer.valueOf( nextFightNumber ) ).getFighterIdRed();
            otherFighter = fightMap.get( Integer.valueOf( nextFightNumber ) ).getFighterRed();

            fightMap.get( Integer.valueOf( nextFightNumber ) ).resetForKoList();
            fightMap.get( Integer.valueOf( nextFightNumber ) ).setFighterIdRed( otherFighterId );
            fightMap.get( Integer.valueOf( nextFightNumber ) ).setFighterRed( otherFighter );

            if ( fighter != null )
            {
                fightMap.get( Integer.valueOf( nextFightNumber ) ).setFighterIdBlue( fighter.getId() );
                fightMap.get( Integer.valueOf( nextFightNumber ) ).setFighterBlue( fighter );
            }
            else
            {
                fightMap.get( Integer.valueOf( nextFightNumber ) ).setFighterIdBlue( LONG_DEFAULT );
                fightMap.get( Integer.valueOf( nextFightNumber ) ).setFighterBlue( null );
            }
        }

        fightMap.get( Integer.valueOf( nextFightNumber ) ).setModificationId( LONG_1 );
        fightMap.get( Integer.valueOf( nextFightNumber ) ).setModificationDate(
            new Timestamp( System.currentTimeMillis() ) );

//		if(nextFightNumber>0)
//			updateFight(fightMap, nextFightNumber, (nextFightNumber % 2 == 0)? FIGHTER_RED:FIGHTER_BLUE, null);

        return null;
    }


    /**
     * update fights in the solace round
     *
     * @param fightMap
     * @param numberOfFight
     * @param redBlue
     * @param fighter
     * @return
     */
    private NewaFight updateSolaceFight( Map<Integer, NewaFight> fightMap, int numberOfFight, int redBlue,
                                         NewaFighter fighter )
    {
        long otherFighterId;
        NewaFighter otherFighter;
        if ( FIGHTER_BLUE == redBlue )
        {
            otherFighterId =
                fightMap.get( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).getFighterIdBlue();
            otherFighter =
                fightMap.get( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).getFighterBlue();

            fightMap.get( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).resetForKoList();
            fightMap.get( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).setFighterIdBlue(
                otherFighterId );
            fightMap.get( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).setFighterBlue(
                otherFighter );

            if ( fighter != null )
            {
                fightMap.get( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).setFighterIdRed(
                    fighter.getId() );
                fightMap.get( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).setFighterRed(
                    fighter );
            }
            else
            {
                fightMap.get( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).setFighterIdRed(
                    LONG_DEFAULT );
                fightMap.get( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).setFighterRed(
                    null );
            }
        }

        if ( FIGHTER_RED == redBlue )
        {
            otherFighterId =
                fightMap.get( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).getFighterIdRed();
            otherFighter =
                fightMap.get( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).getFighterRed();

            fightMap.get( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).resetForKoList();
            fightMap.get( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).setFighterIdRed(
                otherFighterId );
            fightMap.get( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).setFighterRed(
                otherFighter );

            if ( fighter != null )
            {
                fightMap.get( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).setFighterIdBlue(
                    fighter.getId() );
                fightMap.get( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).setFighterBlue(
                    fighter );
            }
            else
            {
                fightMap.get( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).setFighterIdBlue(
                    LONG_DEFAULT );
                fightMap.get( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).setFighterBlue(
                    null );
            }
        }

        fightMap.get( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).setModificationId( LONG_1 );
        fightMap.get( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).setModificationDate(
            new Timestamp( System.currentTimeMillis() ) );

        return fightMap.get( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() );
    }

    
    private void handle3rdPlaceFights( NewaKoClass fc, List<NewaFight> changeFights, NewaFightDao fightDao ) throws JJWDataLayerException
    {
        boolean stopMethod =false;
        
        //when fight for 5th place has no winner, so we must ensure that no blue participant for 3rd place fight exists
        if(LONG_MIN == fc.getFightListLooserMapPoolA().get( 1 ).getWinnerId() && LONG_MIN != fc.getFightListLooserMapPoolA().get( 0 ).getFighterIdBlue()){
            NewaFight fight = fc.getFightListLooserMapPoolA().get( 0 );
            long otherFighterId = fight.getFighterIdRed();
            NewaFighter otherFighter = fight.getFighterRed();
            fight.resetForKoList();
            fight.setFighterRed( otherFighter );
            fight.setFighterIdRed( otherFighterId );
            fight.setFighterBlue( null );           
            fight.setFighterIdBlue( LONG_EMPTY );
           
            fight.setModificationId( LONG_1 );
            fight.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
            changeFights.add( fight );
            stopMethod=true;
        }
        
      //when fight for 5th place has no winner, so we must ensure that no blue participant for 3rd place fight exists
        if(LONG_MIN == fc.getFightListLooserMapPoolB().get( 1 ).getWinnerId() && LONG_MIN != fc.getFightListLooserMapPoolB().get( 0 ).getFighterIdBlue()){
            NewaFight fight = fc.getFightListLooserMapPoolB().get( 0 );
            long otherFighterId = fight.getFighterIdRed();
            NewaFighter otherFighter = fight.getFighterRed();
            fight.resetForKoList();
            fight.setFighterRed( otherFighter );
            fight.setFighterIdRed( otherFighterId );
            fight.setFighterBlue( null );           
            fight.setFighterIdBlue( LONG_EMPTY );
           
            fight.setModificationId( LONG_1 );
            fight.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
            changeFights.add( fight );
            stopMethod=true;
        }
        
        
        
        // only perform this method, when we have red or blue opponents for 3rd place fights
        if (stopMethod 
            || LONG_MIN == fc.getFightListLooserMapPoolA().get( 0 ).getFighterIdRed()
            || LONG_MIN == fc.getFightListLooserMapPoolB().get(0 ).getFighterIdRed()
            || LONG_MIN == fc.getFightListLooserMapPoolA().get( 0 ).getFighterIdBlue()
            || LONG_MIN == fc.getFightListLooserMapPoolB().get( 0 ).getFighterIdBlue())
            return;

        // long
        // blueFighterSolaceRoundA_ID=LONG_EMPTY,blueFighterSolaceRoundB_ID=LONG_EMPTY,redFighterSolaceRoundA_ID=LONG_EMPTY,redFighterSolaceRoundB_ID=LONG_EMPTY;
        long blueFighterSolaceRoundA = LONG_EMPTY, blueFighterSolaceRoundB = LONG_EMPTY;
        NewaFighter redFighterA=fc.getFightListLooserMapPoolA().get( 0 ).getFighterRed();
        NewaFighter redFighterB=fc.getFightListLooserMapPoolB().get( 0 ).getFighterRed();
        long redFighterSolaceRoundA = fc.getFightListLooserMapPoolA().get( 0 ).getFighterIdRed();
        long redFighterSolaceRoundB = fc.getFightListLooserMapPoolB().get( 0 ).getFighterIdRed();
        NewaFight solaceRoundA5thPlaceFight = fc.getFightListLooserMapPoolA().get( Integer.valueOf( 1 ) );
        NewaFight solaceRoundB5thPlaceFight = fc.getFightListLooserMapPoolB().get( Integer.valueOf( 1 ) );

        if ( ( solaceRoundA5thPlaceFight.getFighterIdRed() == solaceRoundA5thPlaceFight.getWinnerId().longValue() && solaceRoundA5thPlaceFight.getFighterIdRed() != LONG_MIN )
            || ( solaceRoundA5thPlaceFight.getWinnerId().longValue() == LONG_MIN
                && solaceRoundA5thPlaceFight.getFighterIdBlue() == LONG_DEFAULT && solaceRoundA5thPlaceFight.getFighterIdRed() > LONG_DEFAULT ) )
        {
            blueFighterSolaceRoundA = solaceRoundA5thPlaceFight.getFighterIdRed();
        }

        if ( ( solaceRoundA5thPlaceFight.getFighterIdBlue() == solaceRoundA5thPlaceFight.getWinnerId().longValue() && solaceRoundA5thPlaceFight.getFighterIdBlue() != LONG_MIN )
            || ( solaceRoundA5thPlaceFight.getWinnerId().longValue() == LONG_MIN
                && solaceRoundA5thPlaceFight.getFighterIdBlue() > LONG_DEFAULT && solaceRoundA5thPlaceFight.getFighterIdRed() == LONG_DEFAULT ) )
        {
            blueFighterSolaceRoundA = solaceRoundA5thPlaceFight.getFighterIdBlue();
        }

        if ( ( solaceRoundB5thPlaceFight.getFighterIdRed() == solaceRoundB5thPlaceFight.getWinnerId().longValue() && solaceRoundB5thPlaceFight.getFighterIdRed() != LONG_MIN )
            || ( solaceRoundB5thPlaceFight.getWinnerId().longValue() == LONG_MIN
                && solaceRoundB5thPlaceFight.getFighterIdBlue() == LONG_DEFAULT && solaceRoundB5thPlaceFight.getFighterIdRed() > LONG_DEFAULT ) )
        {
            blueFighterSolaceRoundB = solaceRoundB5thPlaceFight.getFighterIdRed();
        }

        if ( ( solaceRoundB5thPlaceFight.getFighterIdBlue() == solaceRoundB5thPlaceFight.getWinnerId().longValue() && solaceRoundB5thPlaceFight.getFighterIdBlue() != LONG_MIN )
            || ( solaceRoundB5thPlaceFight.getWinnerId().longValue() == LONG_MIN
                && solaceRoundB5thPlaceFight.getFighterIdBlue() > LONG_DEFAULT && solaceRoundB5thPlaceFight.getFighterIdRed() == LONG_DEFAULT ) )
        {
            blueFighterSolaceRoundB = solaceRoundB5thPlaceFight.getFighterIdBlue();
        }

        if ( LONG_EMPTY == redFighterSolaceRoundA || LONG_EMPTY == blueFighterSolaceRoundA || LONG_EMPTY == redFighterSolaceRoundB
            || LONG_EMPTY == blueFighterSolaceRoundB )
        {

            handle3rdPlaceFightsWithFreeFight( fc, changeFights, fightDao, blueFighterSolaceRoundA,
                                               blueFighterSolaceRoundB, redFighterSolaceRoundA, redFighterSolaceRoundB,redFighterA,redFighterB );
        }
        else
        {
            handle3rdPlaceFightsWith4Participans( fc, changeFights, fightDao, blueFighterSolaceRoundA,
                                                  blueFighterSolaceRoundB, redFighterSolaceRoundA,
                                                  redFighterSolaceRoundB ,redFighterA,redFighterB);
        }

        // test, if there is a free fight for the 3rd place and set the winner
        if ( fc.getFightListLooserMapPoolA().get( 0 ).getFighterIdBlue() == LONG_DEFAULT
            && fc.getFightListLooserMapPoolA().get( 0 ).getFighterIdRed() > LONG_DEFAULT
            && fc.getFightListLooserMapPoolA().get( 0 ).getWinnerId().longValue() == LONG_MIN )
        {

            fc.getFightListLooserMapPoolA().get( 0 ).setWinnerId( fc.getFightListLooserMapPoolA().get( 0 ).getFighterIdRed() );
            changeFights.add( fc.getFightListLooserMapPoolA().get( 0 ) );
        }
        if ( fc.getFightListLooserMapPoolA().get( 0 ).getFighterIdRed() == LONG_DEFAULT
            && fc.getFightListLooserMapPoolA().get( 0 ).getFighterIdBlue() > LONG_DEFAULT
            && fc.getFightListLooserMapPoolA().get( 0 ).getWinnerId().longValue() == LONG_MIN )
        {

            fc.getFightListLooserMapPoolA().get( 0 ).setWinnerId( fc.getFightListLooserMapPoolA().get( 0 ).getFighterIdBlue() );
            changeFights.add( fc.getFightListLooserMapPoolA().get( 0 ) );
        }

        // test, if there is a free fight for the 3rd place and set the winner
        if ( fc.getFightListLooserMapPoolB().get( 0 ).getFighterIdBlue() == LONG_DEFAULT
            && fc.getFightListLooserMapPoolB().get( 0 ).getFighterIdRed() > LONG_DEFAULT
            && fc.getFightListLooserMapPoolB().get( 0 ).getWinnerId().longValue() == LONG_MIN )
        {

            fc.getFightListLooserMapPoolB().get( 0 ).setWinnerId( fc.getFightListLooserMapPoolB().get( 0 ).getFighterIdRed() );
            changeFights.add( fc.getFightListLooserMapPoolA().get( 0 ) );
        }
        if ( fc.getFightListLooserMapPoolB().get( 0 ).getFighterIdRed() == LONG_DEFAULT
            && fc.getFightListLooserMapPoolB().get( 0 ).getFighterIdBlue() > LONG_DEFAULT
            && fc.getFightListLooserMapPoolB().get( 0 ).getWinnerId().longValue() == LONG_MIN )
        {

            fc.getFightListLooserMapPoolB().get( 0 ).setWinnerId( fc.getFightListLooserMapPoolB().get( 0 ).getFighterIdBlue() );
            changeFights.add( fc.getFightListLooserMapPoolB().get( 0 ) );
        }
    }

    private void handle3rdPlaceFightsWithFreeFight( NewaKoClass fc, List<NewaFight> changeFights, NewaFightDao fightDao,
                                                    long blueFighterSolaceRoundA, long blueFighterSolaceRoundB,
                                                    long redFighterSolaceRoundA, long redFighterSolaceRoundB, NewaFighter redFighterA , NewaFighter redFighterB  )
        throws JJWDataLayerException
    {
        if ( LONG_EMPTY != redFighterSolaceRoundA && LONG_EMPTY != blueFighterSolaceRoundA )
        {
            if ( fightDao.isDoneFightRegardlessRedBlue( fc.getId(), redFighterSolaceRoundA,
                                                        blueFighterSolaceRoundA ) )
            {// we need to change --> now test if we are allowed to change
                if ( ( redFighterSolaceRoundB != LONG_EMPTY && !fightDao.isDoneFightRegardlessRedBlue( fc.getId(),
                                                                                                 redFighterSolaceRoundB,
                                                                                                 blueFighterSolaceRoundA ) )
                    || ( blueFighterSolaceRoundB != LONG_EMPTY && !fightDao.isDoneFightRegardlessRedBlue( fc.getId(),
                                                                                                    redFighterSolaceRoundA,
                                                                                                    blueFighterSolaceRoundB ) ) )
                {
                    // no change
                }
                else
                {   // change
                    fc.setThirdPlaceFightsChanged( true );
                    if ( ( redFighterSolaceRoundB == LONG_EMPTY && fc.getFightListLooserMapPoolA().get( 0 ).getFighterIdRed() > LONG_DEFAULT )

                        || ( redFighterSolaceRoundB != LONG_EMPTY && fc.getFightListLooserMapPoolA().get( 0 ).getFighterIdRed() != redFighterSolaceRoundB ) )
                    {
                        NewaFight fight = fc.getFightListLooserMapPoolA().get( 0 );
                        long otherFighterId = fight.getFighterIdBlue();
                        NewaFighter otherFighter = fight.getFighterBlue();
                        fight.resetForKoList();
                        fight.setFighterBlue( otherFighter );
                        fight.setFighterIdBlue( otherFighterId );
                        fight.setFighterRed( redFighterB );
                        if ( redFighterSolaceRoundB != LONG_EMPTY )
                            fight.setFighterIdRed( redFighterSolaceRoundB );
                        else
                            fight.setFighterIdRed( LONG_DEFAULT );
                        fight.setModificationId( LONG_1 );
                        fight.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
                        changeFights.add( fight );
                    }

                    if ( ( redFighterSolaceRoundA == LONG_EMPTY && fc.getFightListLooserMapPoolB().get( 0 ).getFighterIdRed() > LONG_DEFAULT )
                        || ( redFighterSolaceRoundA != LONG_EMPTY && fc.getFightListLooserMapPoolB().get( 0 ).getFighterIdRed() != redFighterSolaceRoundA ) )
                    {
                        NewaFight fight = fc.getFightListLooserMapPoolB().get( 0 );
                        long otherFighterId = fight.getFighterIdBlue();
                        NewaFighter otherFighter = fight.getFighterBlue();
                        fight.resetForKoList();
                        fight.setFighterBlue( otherFighter );
                        fight.setFighterIdBlue( otherFighterId );
                        fight.setFighterRed( redFighterA );
                        fight.setFighterIdRed( redFighterSolaceRoundA );

                        fight.setModificationId( LONG_1 );
                        fight.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
                        changeFights.add( fight );
                    }
                }
            }               
        }
        else
        {
            if ( LONG_EMPTY != redFighterSolaceRoundB && LONG_EMPTY != blueFighterSolaceRoundB )
            {
                if ( fightDao.isDoneFightRegardlessRedBlue(fc.getId(), redFighterSolaceRoundB,
                                                           blueFighterSolaceRoundB )  )
                {// we need to change --> now test if we are allowed to change
                    if ( ( redFighterSolaceRoundA != LONG_EMPTY && !fightDao.isDoneFightRegardlessRedBlue( fc.getId(),
                                                                                                     redFighterSolaceRoundA,
                                                                                                     blueFighterSolaceRoundB) )
                        || ( blueFighterSolaceRoundA != LONG_EMPTY && !fightDao.isDoneFightRegardlessRedBlue( fc.getId(),
                                                                                                        redFighterSolaceRoundB,
                                                                                                        blueFighterSolaceRoundA ) ) )
                    {
                        // no change
                    }
                    else{
                        //change
                        fc.setThirdPlaceFightsChanged( true );
                        if ( ( redFighterSolaceRoundA == LONG_EMPTY && fc.getFightListLooserMapPoolB().get( 0 ).getFighterIdRed() > LONG_DEFAULT )

                            || ( redFighterSolaceRoundA != LONG_EMPTY && fc.getFightListLooserMapPoolB().get( 0 ).getFighterIdRed() != redFighterSolaceRoundA ) )
                        {
                            NewaFight fight = fc.getFightListLooserMapPoolB().get( 0 );
                            long otherFighterId = fight.getFighterIdBlue();
                            NewaFighter otherFighter = fight.getFighterBlue();
                            fight.resetForKoList();
                            fight.setFighterBlue( otherFighter );
                            fight.setFighterIdBlue( otherFighterId );
                            fight.setFighterRed( redFighterA );
                            if ( redFighterSolaceRoundA != LONG_EMPTY )
                                fight.setFighterIdRed( redFighterSolaceRoundA);
                            else
                                fight.setFighterIdRed( LONG_DEFAULT );
                            fight.setModificationId( LONG_1 );
                            fight.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
                            changeFights.add( fight );
                        }

                        if ( ( redFighterSolaceRoundB == LONG_EMPTY && fc.getFightListLooserMapPoolA().get( 0 ).getFighterIdRed() > LONG_DEFAULT )
                            || ( redFighterSolaceRoundB != LONG_EMPTY && fc.getFightListLooserMapPoolA().get( 0 ).getFighterIdRed() != redFighterSolaceRoundB ) )
                        {
                            NewaFight fight = fc.getFightListLooserMapPoolA().get( 0 );
                            long otherFighterId = fight.getFighterIdBlue();
                            NewaFighter otherFighter = fight.getFighterBlue();
                            fight.resetForKoList();
                            fight.setFighterBlue( otherFighter );
                            fight.setFighterIdBlue( otherFighterId );
                            fight.setFighterRed( redFighterB );
                            fight.setFighterIdRed( redFighterSolaceRoundB );

                            fight.setModificationId( LONG_1 );
                            fight.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
                            changeFights.add( fight );
                        }
                    }
                }
                else
                {
                    //no change
                }
            }
        }
    }

    private void handle3rdPlaceFightsWith4Participans( NewaKoClass fc, List<NewaFight> changeFights, NewaFightDao fightDao,
                                                  long blueFighterSolaceRoundA, long blueFighterSolaceRoundB,
                                                  long redFighterSolaceRoundA, long redFighterSolaceRoundB, NewaFighter redFighterA , NewaFighter redFighterB  )
        throws JJWDataLayerException
    {
        // 4 fighter , no DQs
        // 1st: test if we need to change
        // 2nd: are we allowed to change, to avoid that after change is a conflict

        if ( fightDao.isDoneFightRegardlessRedBlue( fc.getId(), redFighterSolaceRoundA,
                                                    blueFighterSolaceRoundA )
            || fightDao.isDoneFightRegardlessRedBlue( fc.getId(), redFighterSolaceRoundB,
                                                      blueFighterSolaceRoundB ) )
        {
            // we need to change
            if ( fightDao.isDoneFightRegardlessRedBlue( fc.getId(), redFighterSolaceRoundA,
                                                        blueFighterSolaceRoundB )
                || fightDao.isDoneFightRegardlessRedBlue( fc.getId(), redFighterSolaceRoundB,
                                                          blueFighterSolaceRoundA) )
            {
                //we are not allowed to change
                if(fc.getFightListLooserMapPoolA().get( 0 ).getFighterIdRed() != redFighterSolaceRoundA){                        

                    NewaFight fight = fc.getFightListLooserMapPoolA().get( 0 );
                    long otherFighterId = fight.getFighterIdBlue();
                    if ( fc.getOriginal3rdPlaceFightA().getFighterIdRed() == redFighterSolaceRoundA
                        && fc.getOriginal3rdPlaceFightA().getFighterIdBlue() == otherFighterId )
                    {
                        // test, if we use the fight from database or reset it
                        NewaFightMapper.mapFightToDB( fc.getOriginal3rdPlaceFightA(), fight, false );
                        fc.getFightListLooserMapPoolA().put( 0, fc.getOriginal3rdPlaceFightA() );
                        changeFights = removeChangedFightFromList( fight.getId(), changeFights );
                    }
                    else
                    {
                        NewaFighter otherFighter = fight.getFighterBlue();
                        fight.resetForKoList();
                        fight.setFighterBlue( otherFighter );
                        fight.setFighterIdBlue( otherFighterId );
                        fight.setFighterRed( redFighterA );
                        fight.setFighterIdRed( redFighterSolaceRoundA );

                        fight.setModificationId( LONG_1 );
                        fight.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
                        changeFights.add( fight );
                    }
                }
                
                if ( fc.getFightListLooserMapPoolB().get( 0 ).getFighterIdRed() != redFighterSolaceRoundB )
                {

                    NewaFight fight = fc.getFightListLooserMapPoolB().get( 0 );
                    long otherFighterId = fight.getFighterIdBlue();
                    if ( fc.getOriginal3rdPlaceFightB().getFighterIdRed() == redFighterSolaceRoundB
                        && fc.getOriginal3rdPlaceFightB().getFighterIdBlue() == otherFighterId )
                    {
                        // test, if we use the fight from database or reset it
                        NewaFightMapper.mapFightToDB( fc.getOriginal3rdPlaceFightB(), fight, false );
                        fc.getFightListLooserMapPoolB().put( 0, fc.getOriginal3rdPlaceFightB() );
                        changeFights = removeChangedFightFromList( fight.getId(), changeFights );
                    }
                    else
                    {
                        NewaFighter otherFighter = fight.getFighterBlue();
                        fight.resetForKoList();
                        fight.setFighterBlue( otherFighter );
                        fight.setFighterIdBlue( otherFighterId );
                        fight.setFighterRed( redFighterB );
                        fight.setFighterIdRed( redFighterSolaceRoundB );

                        fight.setModificationId( LONG_1 );
                        fight.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
                        changeFights.add( fight );
                    }
                }
            }
            else
            {
                // lets change                    
                fc.setThirdPlaceFightsChanged( true );
                if(fc.getFightListLooserMapPoolA().get( 0 ).getFighterIdRed() != redFighterSolaceRoundB){                        

                    NewaFight fight = fc.getFightListLooserMapPoolA().get( 0 );
                    long otherFighterId = fight.getFighterIdBlue();
                    if ( fc.getOriginal3rdPlaceFightA().getFighterIdRed() == redFighterSolaceRoundB
                        && fc.getOriginal3rdPlaceFightA().getFighterIdBlue() == otherFighterId )
                    {
                        // test, if we use the fight from database or reset it
                        NewaFightMapper.mapFightToDB( fc.getOriginal3rdPlaceFightA(), fight, false );
                        fc.getFightListLooserMapPoolA().put( 0, fc.getOriginal3rdPlaceFightA() );
                        changeFights = removeChangedFightFromList( fight.getId(), changeFights );
                    }
                    else
                    {
                        NewaFighter otherFighter = fight.getFighterBlue();
                        fight.resetForKoList();
                        fight.setFighterBlue( otherFighter );
                        fight.setFighterIdBlue( otherFighterId );
                        fight.setFighterRed( redFighterB );
                        fight.setFighterIdRed( redFighterSolaceRoundB );

                        fight.setModificationId( LONG_1 );
                        fight.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
                        changeFights.add( fight );
                    }
                }
                
                if ( fc.getFightListLooserMapPoolB().get( 0 ).getFighterIdRed() != redFighterSolaceRoundA )
                {

                    NewaFight fight = fc.getFightListLooserMapPoolB().get( 0 );
                    long otherFighterId = fight.getFighterIdBlue();
                    if ( fc.getOriginal3rdPlaceFightB().getFighterIdRed() == redFighterSolaceRoundA
                        && fc.getOriginal3rdPlaceFightB().getFighterIdBlue() == otherFighterId )
                    {
                        // test, if we use the fight from database or reset it
                        NewaFightMapper.mapFightToDB( fc.getOriginal3rdPlaceFightB(), fight, false );
                        fc.getFightListLooserMapPoolB().put( 0, fc.getOriginal3rdPlaceFightB() );
                        changeFights = removeChangedFightFromList( fight.getId(), changeFights );
                    }
                    else
                    {
                        NewaFighter otherFighter = fight.getFighterBlue();
                        fight.resetForKoList();
                        fight.setFighterBlue( otherFighter );
                        fight.setFighterIdBlue( otherFighterId );
                        fight.setFighterRed( redFighterA );
                        fight.setFighterIdRed( redFighterSolaceRoundA );

                        fight.setModificationId( LONG_1 );
                        fight.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
                        changeFights.add( fight );
                    }
                }
            }
        }
    }

    private List<NewaFight> removeChangedFightFromList( long fightId, List<NewaFight> changeFights )
    {
        int index = 0;
        for ( int i = 0; i < changeFights.size(); i++ )
        {
            if ( changeFights.get( i ).getId() != null && changeFights.get( i ).getId() == fightId )
            {
                index =i;
                break;
            }
        }
        changeFights.remove( index );
        return changeFights;
    }
}