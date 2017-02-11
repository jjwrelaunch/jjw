/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DuoclassKoImpl.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:46
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

package de.jjw.service.impl.duo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.jjw.dao.duo.DuoFightDao;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.model.duo.DuoFight;
import de.jjw.model.duo.DuoKoClass;
import de.jjw.model.duo.DuoTeam;
import de.jjw.service.impl.KoImplAbstract;
import de.jjw.service.impl.SolaceDTO;
import de.jjw.service.mapper.duo.DuoFightMapper;
import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;

public class DuoclassKoImpl
    extends KoImplAbstract
    implements IValueConstants
{


    public DuoKoClass doKo( DuoKoClass fc, DuoFightDao duoDao )
        throws JJWDataLayerException
    {

        doMainRoundDummyFights( fc );

        List<DuoFight> changeFights = new ArrayList<DuoFight>();
        int numberOfFight = fc.getFightListMapPoolA().size() - 1;
        DuoFight fight = null;
        for (; numberOfFight >= 0; numberOfFight-- )
        {
            fight = fc.getFightListMapPoolA().get( Integer.valueOf( numberOfFight ) );

            if ( ( fight.getTeamIdRed() == fight.getWinnerId() && fight.getTeamIdRed() != LONG_MIN ) ||
                fight.getWinnerId() == LONG_MIN && fight.getTeamIdBlue() == LONG_DEFAULT &&
                    fight.getTeamIdRed() != LONG_DEFAULT )
            {
                // test, ob weiter gesetzt
                if ( numberOfFight > 0 && numberOfFight % 2 == 0 && fight.getTeamIdRed() !=
                    fc.getFightListMapPoolA().get( Integer.valueOf( ( numberOfFight - 1 ) / 2 ) ).getTeamIdBlue() )
                {
                    //update Fight, clear Inputs and overwrite
                    changeFights.add(updateFight( fc.getFightListMapPoolA(), numberOfFight, FIGHTER_RED, fight.getDuoTeamRed() ) );
                }

                if ( numberOfFight > 0 && numberOfFight % 2 != 0 && fight.getTeamIdRed() !=
                    fc.getFightListMapPoolA().get( Integer.valueOf( ( numberOfFight - 1 ) / 2 ) ).getTeamIdRed() )
                {
                    changeFights.add(updateFight( fc.getFightListMapPoolA(), numberOfFight, FIGHTER_BLUE, fight.getDuoTeamRed() ) );
                }
                changeFights.add( updateSolaceRound( fc, fight, numberOfFight, true ) );
            }

            // when firstRound and no fighter then set free fight in solace Round
            if ( fight.getTeamIdBlue() == LONG_MIN && fight.getTeamIdRed() == LONG_MIN &&
                isFirstRound( numberOfFight, fc.getTeamListPoolA().size() + fc.getTeamListPoolA().size() ) )
            {
                updateSolaceRound( fc, fight, numberOfFight, true );
                continue;
            }

            if ( ( fight.getTeamIdBlue() == fight.getWinnerId() && fight.getTeamIdBlue() != LONG_MIN ) ||
                fight.getWinnerId() == LONG_MIN && fight.getTeamIdBlue() != LONG_DEFAULT &&
                    fight.getTeamIdRed() == LONG_DEFAULT )
            {
                // test, ob weiter gesetzt
                if ( numberOfFight > 0 && numberOfFight % 2 == 0 && fight.getTeamIdBlue() !=
                    fc.getFightListMapPoolA().get( ( numberOfFight - 1 ) / 2 ).getTeamIdBlue() )
                {
                    changeFights.add(updateFight( fc.getFightListMapPoolA(), numberOfFight, FIGHTER_RED, fight.getDuoTeamBlue() ) );
                }

                if ( numberOfFight > 0 && numberOfFight % 2 != 0 &&
                    fight.getTeamIdBlue() != fc.getFightListMapPoolA().get( ( numberOfFight - 1 ) / 2 ).getTeamIdRed() )
                {
                    //update Fight, clear Inputs and overwrite
                    changeFights.add( updateFight( fc.getFightListMapPoolA(), numberOfFight, FIGHTER_BLUE, fight.getDuoTeamBlue() ) );
                }
                changeFights.add( updateSolaceRound( fc, fight, numberOfFight, true ) );

            }

            // dq
            if ( fight.getWinnerId() == LONG_DEFAULT )
            {
                if ( numberOfFight > 0 && numberOfFight % 2 != 0 && LONG_DEFAULT != fight.getTeamIdRed() )
                {
                    changeFights.add( updateFight( fc.getFightListMapPoolA(), numberOfFight, FIGHTER_BLUE, null ) );
                }

                if ( numberOfFight > 0 && numberOfFight % 2 == 0 && LONG_DEFAULT != fight.getTeamIdBlue() )
                {
                    changeFights.add( updateFight( fc.getFightListMapPoolA(), numberOfFight, FIGHTER_RED, null ) );
                }
                changeFights.add( updateSolaceRound( fc, fight, numberOfFight, true ) );
            }

            // free fight
            if ( fight.getWinnerId() == LONG_MIN && fight.getTeamIdBlue() == LONG_DEFAULT &&
                fight.getTeamIdRed() == LONG_DEFAULT )
            {

            }
        }

        numberOfFight = fc.getFightListMapPoolB().size() - 1;
        fight = null;
        for (; numberOfFight >= 0; numberOfFight-- )
        {
            fight = fc.getFightListMapPoolB().get( numberOfFight );

            if ( ( fight.getTeamIdRed() == fight.getWinnerId() && fight.getTeamIdRed() != LONG_MIN ) ||
                fight.getWinnerId() == LONG_MIN && fight.getTeamIdBlue() == LONG_DEFAULT &&
                    fight.getTeamIdRed() != LONG_DEFAULT )
            {
                // test, ob weiter gesetzt
                if ( numberOfFight > 0 && numberOfFight % 2 == 0 &&
                    fight.getTeamIdRed() != fc.getFightListMapPoolB().get( ( numberOfFight - 1 ) / 2 ).getTeamIdBlue() )
                {
                    changeFights.add(updateFight( fc.getFightListMapPoolB(), numberOfFight, FIGHTER_RED, fight.getDuoTeamRed() ) );
                }

                if ( numberOfFight > 0 && numberOfFight % 2 != 0 &&
                    fight.getTeamIdRed() != fc.getFightListMapPoolB().get( ( numberOfFight - 1 ) / 2 ).getTeamIdRed() )
                {
                    changeFights.add( updateFight( fc.getFightListMapPoolB(), numberOfFight, FIGHTER_BLUE, fight.getDuoTeamRed() ) );
                }
                changeFights.add( updateSolaceRound( fc, fight, numberOfFight, false ) );
            }

            // when firstRound and no fighter then set free fight in solace Round
            if ( fight.getTeamIdBlue() == LONG_MIN && fight.getTeamIdRed() == LONG_MIN &&
                isFirstRound( numberOfFight, fc.getTeamListPoolB().size() + fc.getTeamListPoolB().size() ) )
            {
                updateSolaceRound( fc, fight, numberOfFight, false );
                continue;
            }

            if ( ( fight.getTeamIdBlue() == fight.getWinnerId() && fight.getTeamIdBlue() != LONG_MIN ) ||
                fight.getWinnerId() == LONG_MIN && fight.getTeamIdBlue() != LONG_DEFAULT &&
                    fight.getTeamIdRed() == LONG_DEFAULT )
            {
                // test, ob weiter gesetzt
                if ( numberOfFight > 0 && numberOfFight % 2 == 0 && fight.getTeamIdBlue() !=
                    fc.getFightListMapPoolB().get( ( numberOfFight - 1 ) / 2 ).getTeamIdBlue() )
                {
                    changeFights.add( updateFight( fc.getFightListMapPoolB(), numberOfFight, FIGHTER_RED, fight.getDuoTeamBlue() ) );
                }

                if ( numberOfFight > 0 && numberOfFight % 2 != 0 &&
                    fight.getTeamIdBlue() != fc.getFightListMapPoolB().get( ( numberOfFight - 1 ) / 2 ).getTeamIdRed() )
                {
                    //update Fight, clear Inputs and overwrite
                    changeFights.add(updateFight( fc.getFightListMapPoolB(), numberOfFight, FIGHTER_BLUE, fight.getDuoTeamBlue() ) );
                }
                changeFights.add( updateSolaceRound( fc, fight, numberOfFight, false ) );
            }

            // dq
            if ( fight.getWinnerId() == LONG_DEFAULT )
            {
                if ( numberOfFight > 0 && numberOfFight % 2 != 0 && LONG_DEFAULT != fight.getTeamIdRed() )
                {
                    changeFights.add( updateFight( fc.getFightListMapPoolB(), numberOfFight, FIGHTER_BLUE, null ) );
                }

                if ( numberOfFight > 0 && numberOfFight % 2 == 0 && LONG_DEFAULT != fight.getTeamIdBlue() )
                {
                    changeFights.add( updateFight( fc.getFightListMapPoolB(), numberOfFight, FIGHTER_RED, null ) );
                }
                changeFights.add( updateSolaceRound( fc, fight, numberOfFight, false ) );
            }

            // free fight
            if ( fight.getWinnerId() == LONG_MIN && fight.getTeamIdBlue() == LONG_DEFAULT &&
                fight.getTeamIdRed() == LONG_DEFAULT )
            {

            }
        }
        //handleFreeLoserRound()
        handleLoserRoundA( fc, changeFights );
        handleLoserRoundB( fc, changeFights );
        handle3rdPlaceFights(fc, changeFights, duoDao);

        //handle final fight
        changeFights.add( handleFinal( fc ) );

        for ( int i = 0; i < fc.getFightListLooserMapPoolA().size(); i++ )
        {
            if ( fc.getFightListLooserMapPoolA().get( i ).isDirty() &&
                fc.getFightListLooserMapPoolA().get( i ).getId() != null )
            {
                duoDao.saveFight( fc.getFightListLooserMapPoolA().get( i ) );
            }
        }

        for ( int i = 0; i < fc.getFightListLooserMapPoolB().size(); i++ )
        {
            if ( fc.getFightListLooserMapPoolB().get( i ).isDirty() &&
                fc.getFightListLooserMapPoolB().get( i ).getId() != null )
            {
                duoDao.saveFight( fc.getFightListLooserMapPoolB().get( i ) );
            }
        }

        for ( DuoFight changedFight : changeFights )
        {
            if ( changedFight != null && changedFight.getId() != null )
            // FightMapper.mapFightToDB(FightMapper.mapFightFromDB(changedFight),
            // fightDao.getFight(changedFight.getId()), false);
            {
                if ( fight.getWinnerId() < TypeUtil.LONG_DEFAULT )
                {
                    changedFight.setModificationId( LONG_1 );
                    changedFight.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
                }
                duoDao.saveFight( changedFight );
            }
        }
        return fc;
    }


    /**
     * @param fc
     */
    private void doMainRoundDummyFights( DuoKoClass fc )
    {
        // create empty dummy fights, which are not stored in db
        int mainRoundFights = DuoclassKoCreateImpl.getNumberOfFightsInMainRound(
            fc.getTeamListPoolA().size() + fc.getTeamListPoolB().size() ) / 2;
        int poolsize = DuoclassKoCreateImpl.getPoolsize( fc.getTeamListPoolA().size() + fc.getTeamListPoolB().size() );
        DuoFight fight;
        for ( int i = 0; i < mainRoundFights; i++ )
        {
            if ( fc.getFightListMapPoolA().get( i ) == null )
            {
                fight = new DuoFight();
                setTechnicalDBFieldsForDummyFight( fight );
                fight.setId( TypeUtil.LONG_OBJECT_EMPTY );
                // set the fighter in the dummy fight, which had a free fight
                if ( i >= ( poolsize / 4 - 1 ) )
                {
                    if ( i % 2 != 0 )
                    {
                        fight.setDuoTeamRed( fc.getFightListMapPoolA().get( ( i + 1 ) / 2 - 1 ).getDuoTeamRed() );
                    }
                    else
                    {
                        fight.setDuoTeamRed( fc.getFightListMapPoolA().get( ( i + 1 ) / 2 - 1 ).getDuoTeamBlue() );
                    }
                }
                fc.getFightListMapPoolA().put( i, fight );
            }

            if ( fc.getFightListMapPoolB().get( i ) == null )
            {
                fight = new DuoFight();
                setTechnicalDBFieldsForDummyFight( fight );
                fight.setId( TypeUtil.LONG_OBJECT_EMPTY );
                // set the fighter in the dummy fight, which had a free fight
                if ( i >= ( poolsize / 4 - 1 ) )
                {
                    if ( i % 2 != 0 )
                    {
                        fight.setDuoTeamRed( fc.getFightListMapPoolB().get( ( i + 1 ) / 2 - 1 ).getDuoTeamRed() );
                    }
                    else
                    {
                        fight.setDuoTeamRed( fc.getFightListMapPoolB().get( ( i + 1 ) / 2 - 1 ).getDuoTeamBlue() );
                    }
                }
                fc.getFightListMapPoolB().put( i, fight );
            }

        }
    }


    /**
     * handles the final-fight
     *
     * @param fc
     * @return
     */
    private DuoFight handleFinal( DuoKoClass fc )
    {
        boolean isFinalUpdate = false;
        if ( fc.getFightListMapPoolA().get( 0 ).getWinnerId() != fc.getFinalFight().getTeamIdRed() )
        {

            long otherDuoTeamId;
            DuoTeam otherDuoTeam;

            otherDuoTeamId = fc.getFinalFight().getTeamIdBlue();
            otherDuoTeam = fc.getFinalFight().getDuoTeamBlue();

            fc.getFinalFight().resetForKoList();
            fc.getFinalFight().setTeamIdBlue( otherDuoTeamId );
            fc.getFinalFight().setDuoTeamBlue( otherDuoTeam );

            if ( fc.getFightListMapPoolA().get( 0 ).getWinnerId() == fc.getFightListMapPoolA().get( 0 ).getTeamIdRed() )
            {
                // fighter red is in the final
                fc.getFinalFight().setTeamIdRed( fc.getFightListMapPoolA().get( 0 ).getTeamIdRed() );
                fc.getFinalFight().setDuoTeamRed( fc.getFightListMapPoolA().get( 0 ).getDuoTeamRed() );
            }

            if ( fc.getFightListMapPoolA().get( 0 ).getWinnerId() ==
                fc.getFightListMapPoolA().get( 0 ).getTeamIdBlue() )
            {
                // fighter blue is in the final
                fc.getFinalFight().setTeamIdRed( fc.getFightListMapPoolA().get( 0 ).getTeamIdBlue() );
                fc.getFinalFight().setDuoTeamRed( fc.getFightListMapPoolA().get( 0 ).getDuoTeamBlue() );
            }

            if ( fc.getFightListMapPoolA().get( 0 ).getWinnerId() == LONG_DEFAULT )
            {
                // no fighter in the final
                fc.getFinalFight().setTeamIdRed( LONG_DEFAULT );
                fc.getFinalFight().setDuoTeamRed( null );

            }
            isFinalUpdate = true;
        }

        if ( fc.getFightListMapPoolB().get( 0 ).getWinnerId() != fc.getFinalFight().getTeamIdBlue() )
        {

            long otherDuoTeamId;
            DuoTeam otherDuoTema;

            otherDuoTeamId = fc.getFinalFight().getTeamIdRed();
            otherDuoTema = fc.getFinalFight().getDuoTeamRed();

            fc.getFinalFight().resetForKoList();
            fc.getFinalFight().setTeamIdRed( otherDuoTeamId );
            fc.getFinalFight().setDuoTeamRed( otherDuoTema );

            if ( fc.getFightListMapPoolB().get( 0 ).getWinnerId() == fc.getFightListMapPoolB().get( 0 ).getTeamIdRed() )
            {
                // fighter red is in the final
                fc.getFinalFight().setTeamIdBlue( fc.getFightListMapPoolB().get( 0 ).getTeamIdRed() );
                fc.getFinalFight().setDuoTeamBlue( fc.getFightListMapPoolB().get( 0 ).getDuoTeamRed() );
            }

            if ( fc.getFightListMapPoolB().get( 0 ).getWinnerId() ==
                fc.getFightListMapPoolB().get( 0 ).getTeamIdBlue() )
            {
                // fighter blue is in the final
                fc.getFinalFight().setTeamIdBlue( fc.getFightListMapPoolB().get( 0 ).getTeamIdBlue() );
                fc.getFinalFight().setDuoTeamBlue( fc.getFightListMapPoolB().get( 0 ).getDuoTeamBlue() );
            }

            if ( fc.getFightListMapPoolA().get( 0 ).getWinnerId() == LONG_DEFAULT )
            {
                // no fighter in the final
                fc.getFinalFight().setTeamIdBlue( LONG_DEFAULT );
                fc.getFinalFight().setDuoTeamBlue( null );

            }
            isFinalUpdate = true;
        }

        if ( isFinalUpdate )
        {

            if ( fc.getFinalFight().getTeamIdRed() == LONG_DEFAULT &&
                fc.getFinalFight().getTeamIdBlue() > LONG_DEFAULT )
            {
                fc.getFinalFight().setWinnerId( fc.getFinalFight().getTeamIdBlue() );
            }

            if ( fc.getFinalFight().getTeamIdBlue() == LONG_DEFAULT &&
                fc.getFinalFight().getTeamIdRed() > LONG_DEFAULT )
            {
                fc.getFinalFight().setWinnerId( fc.getFinalFight().getTeamIdRed() );
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
    private void handleLoserRoundA( DuoKoClass fc, List<DuoFight> changeFights )
    {
        int numberOfFight;
        DuoFight fight;
        //handle solace rounds
        numberOfFight = fc.getFightListLooserMapPoolA().size() - 1;
        for (; numberOfFight > 0; numberOfFight-- )
        {
            fight = fc.getFightListLooserMapPoolA().get( numberOfFight );

            if ( ( fight.getTeamIdRed() == fight.getWinnerId() && fight.getTeamIdRed() != LONG_MIN ) ||
                fight.getWinnerId() == LONG_MIN && fight.getTeamIdBlue() == LONG_DEFAULT &&
                    fight.getTeamIdRed() > LONG_DEFAULT )
            {
                // test, ob weiter gesetzt
//				if(numberOfFight %2 == 0){
                if ( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getRedBlue() == FIGHTER_RED && fight.getTeamIdRed() !=
                    fc.getFightListLooserMapPoolA().get(
                        SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).getTeamIdRed() )
                {
                    changeFights.add( updateSolaceFight( fc.getFightListLooserMapPoolA(), numberOfFight, FIGHTER_BLUE,
                                                         fight.getDuoTeamRed() ) );
                }
                if ( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getRedBlue() == FIGHTER_BLUE && fight.getTeamIdRed() !=
                    fc.getFightListLooserMapPoolA().get(
                        SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).getTeamIdBlue() )
                {
                    changeFights.add( updateSolaceFight( fc.getFightListLooserMapPoolA(), numberOfFight, FIGHTER_RED,
                                                         fight.getDuoTeamRed() ) );
                }

            }

            if ( ( fight.getTeamIdBlue() == fight.getWinnerId() && fight.getTeamIdBlue() != LONG_MIN ) ||
                fight.getWinnerId() == LONG_MIN && fight.getTeamIdBlue() > LONG_DEFAULT &&
                    fight.getTeamIdRed() == LONG_DEFAULT )
            {
                // test, ob weiter gesetzt
//				if(numberOfFight %2 == 0){
                if ( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getRedBlue() == FIGHTER_RED && fight.getTeamIdBlue() !=
                    fc.getFightListLooserMapPoolA().get(
                        SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).getTeamIdRed() )
                {
                    changeFights.add( updateSolaceFight( fc.getFightListLooserMapPoolA(), numberOfFight, FIGHTER_BLUE,
                                                         fight.getDuoTeamBlue() ) );
                }
                if ( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getRedBlue() == FIGHTER_BLUE && fight.getTeamIdBlue() !=
                    fc.getFightListLooserMapPoolA().get(
                        SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).getTeamIdBlue() )
                {
                    changeFights.add( updateSolaceFight( fc.getFightListLooserMapPoolA(), numberOfFight, FIGHTER_RED,
                                                         fight.getDuoTeamBlue() ) );
                }

            }

            // dq
            if ( fight.getWinnerId() == LONG_DEFAULT )
            {
                if ( numberOfFight % 2 != 0 && LONG_DEFAULT != fight.getTeamIdRed() )
                {
                    changeFights.add( updateSolaceFight( fc.getFightListLooserMapPoolA(), numberOfFight, FIGHTER_BLUE, null ) );
                }
            }

            if ( fight.getWinnerId() == LONG_DEFAULT )
            {
                if ( numberOfFight % 2 == 0 && LONG_DEFAULT != fight.getTeamIdBlue() )
                {
                    changeFights.add( updateSolaceFight( fc.getFightListLooserMapPoolA(), numberOfFight, FIGHTER_RED, null ) );
                }
            }

            // free fight, because of no firstround fighter
            if ( fight.getWinnerId() == LONG_MIN && fight.getTeamIdBlue() == LONG_DEFAULT &&
                fight.getTeamIdRed() == LONG_DEFAULT )
            {
                updateSolaceFight( fc.getFightListLooserMapPoolA(), numberOfFight, FIGHTER_RED, null );


            }
        }
    }


    /**
     * handles the LoserRoundA
     *
     * @param fc
     * @param changeFights
     */
    private void handleLoserRoundB( DuoKoClass fc, List<DuoFight> changeFights )
    {
        int numberOfFight;
        DuoFight fight;
        //handle solace rounds
        numberOfFight = fc.getFightListLooserMapPoolB().size() - 1;
        for (; numberOfFight > 0; numberOfFight-- )
        {
            fight = fc.getFightListLooserMapPoolB().get( Integer.valueOf( numberOfFight ) );

            if ( ( fight.getTeamIdRed() == fight.getWinnerId() && fight.getTeamIdRed() != LONG_MIN ) ||
                fight.getWinnerId() == LONG_MIN && fight.getTeamIdBlue() == LONG_DEFAULT &&
                    fight.getTeamIdRed() != LONG_DEFAULT )
            {
                // test, ob weiter gesetzt
                if ( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getRedBlue() == FIGHTER_RED && fight.getTeamIdRed() !=
                    fc.getFightListLooserMapPoolB().get(
                        SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).getTeamIdRed() )
                {
                    changeFights.add( updateSolaceFight( fc.getFightListLooserMapPoolB(), numberOfFight, FIGHTER_BLUE,
                                                         fight.getDuoTeamRed() ) );
                }
                if ( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getRedBlue() == FIGHTER_BLUE && fight.getTeamIdRed() !=
                    fc.getFightListLooserMapPoolB().get(
                        SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).getTeamIdBlue() )
                {
                    changeFights.add( updateSolaceFight( fc.getFightListLooserMapPoolB(), numberOfFight, FIGHTER_RED,
                                                         fight.getDuoTeamRed() ) );
                }
            }

            if ( ( fight.getTeamIdBlue() == fight.getWinnerId() && fight.getTeamIdBlue() != LONG_MIN ) ||
                fight.getWinnerId() == LONG_MIN && fight.getTeamIdBlue() != LONG_DEFAULT &&
                    fight.getTeamIdRed() == LONG_DEFAULT )
            {
                // test, ob weiter gesetzt
                if ( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getRedBlue() == FIGHTER_RED && fight.getTeamIdBlue() !=
                    fc.getFightListLooserMapPoolB().get(
                        SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).getTeamIdRed() )
                {
                    changeFights.add( updateSolaceFight( fc.getFightListLooserMapPoolB(), numberOfFight, FIGHTER_BLUE,
                                                         fight.getDuoTeamBlue() ) );
                }
                if ( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getRedBlue() == FIGHTER_BLUE && fight.getTeamIdBlue() !=
                    fc.getFightListLooserMapPoolB().get(
                        SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).getTeamIdBlue() )
                {
                    changeFights.add( updateSolaceFight( fc.getFightListLooserMapPoolB(), numberOfFight, FIGHTER_RED,
                                                         fight.getDuoTeamBlue() ) );
                }
            }

            // dq
            if ( fight.getWinnerId() == LONG_DEFAULT )
            {
                if ( numberOfFight % 2 != 0 && LONG_DEFAULT != fight.getTeamIdRed() )
                {
                    changeFights.add( updateSolaceFight( fc.getFightListLooserMapPoolB(), numberOfFight, FIGHTER_BLUE, null ) );
                }
            }

            if ( fight.getWinnerId() == LONG_DEFAULT )
            {
                if ( numberOfFight % 2 == 0 && LONG_DEFAULT != fight.getTeamIdBlue() )
                {
                    changeFights.add( updateSolaceFight( fc.getFightListLooserMapPoolB(), numberOfFight, FIGHTER_RED, null ) );
                }
            }

            // free fight
            if ( fight.getWinnerId() == LONG_MIN && fight.getTeamIdBlue() == LONG_DEFAULT &&
                fight.getTeamIdRed() == LONG_DEFAULT )
            {
                updateSolaceFight( fc.getFightListLooserMapPoolB(), numberOfFight, FIGHTER_RED, null );

            }
        }
    }


    private DuoFight updateSolaceRound( DuoKoClass fc, DuoFight actualFight, int actualFightNumber, boolean isPoolA )
    {

        DuoFight solaceFight = null;
        int redBlue = 0;
        SolaceDTO dto = null;

        switch ( getPoolsize( fc.getTeamListPoolA().size() + fc.getTeamListPoolB().size() ) )
        {

            case SIZE_KO_16:
                dto = SOLACE_MA_16.get( actualFightNumber );
                break;

            case SIZE_KO_32:
                dto = SOLACE_MA_32.get( actualFightNumber );
                break;

            case SIZE_KO_64:
                dto = SOLACE_MA_64.get( actualFightNumber );
                break;

        }

        if ( isPoolA && dto != null && dto.isChange() )
        {
            solaceFight = fc.getFightListLooserMapPoolB().get( dto.getNumberInSolaceRound() );
            redBlue = dto.getRedBlue();
        }

        if ( !isPoolA && dto != null && dto.isChange() )
        {
            solaceFight = fc.getFightListLooserMapPoolA().get( dto.getNumberInSolaceRound() );
            redBlue = dto.getRedBlue();
        }

        if ( isPoolA && dto != null && !dto.isChange() )
        {
            solaceFight = fc.getFightListLooserMapPoolA().get( dto.getNumberInSolaceRound() );
            redBlue = dto.getRedBlue();
        }

        if ( !isPoolA && dto != null && !dto.isChange() )
        {
            solaceFight = fc.getFightListLooserMapPoolB().get( dto.getNumberInSolaceRound() );
            redBlue = dto.getRedBlue();
        }
        // case: no one set to loserRound in first round
        if ( actualFight.getWinnerId() == TypeUtil.LONG_EMPTY && actualFight.getTeamIdBlue() == TypeUtil.LONG_EMPTY &&
            actualFight.getTeamIdRed() == TypeUtil.LONG_EMPTY )
        {
            updateSolaceFight( solaceFight, redBlue, null );
            return solaceFight;
        }

        // cases: double DQ , free fight, red won, blue won
        if ( actualFight.getWinnerId() == 0 )
        {
            // double DQ
            if ( FIGHTER_RED == redBlue && solaceFight.getTeamIdRed() != 0 )
            {
                updateSolaceFight( solaceFight, redBlue, null );
            }

            if ( FIGHTER_BLUE == redBlue && solaceFight.getTeamIdBlue() != 0 )
            {
                updateSolaceFight( solaceFight, redBlue, null );
            }
        }

        if ( actualFight.getWinnerId() == actualFight.getTeamIdBlue() )
        {
            if ( FIGHTER_RED == redBlue && solaceFight.getTeamIdRed() != actualFight.getTeamIdRed() )
            {
                updateSolaceFight( solaceFight, redBlue, actualFight.getDuoTeamRed() );
            }

            if ( FIGHTER_BLUE == redBlue && solaceFight.getTeamIdBlue() != actualFight.getTeamIdRed() )
            {
                updateSolaceFight( solaceFight, redBlue, actualFight.getDuoTeamRed() );
            }
        }

        if ( actualFight.getWinnerId() == actualFight.getTeamIdRed() )
        {
            if ( FIGHTER_RED == redBlue && solaceFight.getTeamIdBlue() != actualFight.getTeamIdBlue() )
            {
                updateSolaceFight( solaceFight, redBlue, actualFight.getDuoTeamBlue() );
            }

            if ( FIGHTER_BLUE == redBlue && solaceFight.getTeamIdRed() != actualFight.getTeamIdBlue() )
            {
                updateSolaceFight( solaceFight, redBlue, actualFight.getDuoTeamBlue() );
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
     * @param duoTeam
     */
    private void updateSolaceFight( DuoFight fight, int redBlue, DuoTeam duoTeam )
    {
        long otherDuoTeamId;
        DuoTeam otherDuoTeam;

        if ( duoTeam == null )
        {
            if ( FIGHTER_BLUE == redBlue && fight.getTeamIdBlue() != LONG_DEFAULT )
            {
                otherDuoTeam = fight.getDuoTeamRed();
                otherDuoTeamId = fight.getTeamIdRed();
                fight.resetForKoList();
                fight.setTeamIdRed( otherDuoTeamId );
                fight.setDuoTeamRed( otherDuoTeam );
                fight.setTeamIdBlue( LONG_DEFAULT );
                fight.setDuoTeamBlue( null );
            }

            if ( FIGHTER_RED == redBlue && fight.getTeamIdRed() != LONG_DEFAULT )
            {
                otherDuoTeam = fight.getDuoTeamBlue();
                otherDuoTeamId = fight.getTeamIdBlue();
                fight.resetForKoList();
                fight.setTeamIdBlue( otherDuoTeamId );
                fight.setDuoTeamBlue( otherDuoTeam );
                fight.setTeamIdRed( LONG_DEFAULT );
                fight.setDuoTeamRed( null );
            }
        }
        else
        {
            if ( FIGHTER_BLUE == redBlue && fight.getTeamIdBlue() != duoTeam.getId() )
            {
                otherDuoTeam = fight.getDuoTeamRed();
                otherDuoTeamId = fight.getTeamIdRed();
                fight.resetForKoList();
                fight.setTeamIdRed( otherDuoTeamId );
                fight.setDuoTeamRed( otherDuoTeam );
                fight.setTeamIdBlue( duoTeam.getId() );
                fight.setDuoTeamBlue( duoTeam );
            }
            if ( FIGHTER_RED == redBlue && fight.getTeamIdRed() != duoTeam.getId() )
            {
                otherDuoTeam = fight.getDuoTeamBlue();
                otherDuoTeamId = fight.getTeamIdBlue();
                fight.resetForKoList();
                fight.setTeamIdBlue( otherDuoTeamId );
                fight.setDuoTeamBlue( otherDuoTeam );
                fight.setTeamIdRed( duoTeam.getId() );
                fight.setDuoTeamRed( duoTeam );
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
     * @param duoTeam
     * @return
     */
    private DuoFight updateFight( Map<Integer, DuoFight> fightMap, int numberOfFight, int redBlue, DuoTeam duoTeam )
    {
        long otherTeamId;
        int nextFightNumber = ( numberOfFight - 1 ) / 2;
        DuoTeam otherDuoTeam;
        if ( FIGHTER_BLUE == redBlue )
        {
            otherTeamId = fightMap.get( nextFightNumber ).getTeamIdBlue();
            otherDuoTeam = fightMap.get( nextFightNumber ).getDuoTeamBlue();

            fightMap.get( nextFightNumber ).resetForKoList();
            fightMap.get( nextFightNumber ).setTeamIdBlue( otherTeamId );
            fightMap.get( nextFightNumber ).setDuoTeamBlue( otherDuoTeam );

            if ( duoTeam != null )
            {
                fightMap.get( nextFightNumber ).setTeamIdRed( duoTeam.getId() );
                fightMap.get( nextFightNumber ).setDuoTeamRed( duoTeam );
            }
            else
            {
                fightMap.get( nextFightNumber ).setTeamIdRed( LONG_DEFAULT );
                fightMap.get( nextFightNumber ).setDuoTeamRed( null );
            }

        }

        if ( FIGHTER_RED == redBlue )
        {
            otherTeamId = fightMap.get( nextFightNumber ).getTeamIdRed();
            otherDuoTeam = fightMap.get( nextFightNumber ).getDuoTeamRed();

            fightMap.get( nextFightNumber ).resetForKoList();
            fightMap.get( nextFightNumber ).setTeamIdRed( otherTeamId );
            fightMap.get( nextFightNumber ).setDuoTeamRed( otherDuoTeam );

            if ( duoTeam != null )
            {
                fightMap.get( nextFightNumber ).setTeamIdBlue( duoTeam.getId() );
                fightMap.get( nextFightNumber ).setDuoTeamBlue( duoTeam );
            }
            else
            {
                fightMap.get( nextFightNumber ).setTeamIdBlue( LONG_DEFAULT );
                fightMap.get( nextFightNumber ).setDuoTeamBlue( null );
            }
        }

        fightMap.get( nextFightNumber ).setModificationId( LONG_1 );
        fightMap.get( nextFightNumber ).setModificationDate( new Timestamp( System.currentTimeMillis() ) );

        if ( nextFightNumber > 0 )
        {
            updateFight( fightMap, nextFightNumber, ( nextFightNumber % 2 == 0 ) ? FIGHTER_RED : FIGHTER_BLUE, null );
        }

        return null;
    }


    /**
     * update fights in the solace round
     *
     * @param fightMap
     * @param numberOfFight
     * @param redBlue
     * @param duoTeam
     * @return
     */
    private DuoFight updateSolaceFight( Map<Integer, DuoFight> fightMap, int numberOfFight, int redBlue,
                                        DuoTeam duoTeam )
    {
        long otherTeamId;
        DuoTeam otherDuoTeam;
        if ( FIGHTER_BLUE == redBlue )
        {
            otherTeamId =
                fightMap.get( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).getTeamIdBlue();
            otherDuoTeam =
                fightMap.get( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).getDuoTeamBlue();

            fightMap.get( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).resetForKoList();
            fightMap.get( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).setTeamIdBlue(
                otherTeamId );
            fightMap.get( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).setDuoTeamBlue(
                otherDuoTeam );

            if ( duoTeam != null )
            {
                fightMap.get( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).setTeamIdRed(
                    duoTeam.getId() );
                fightMap.get( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).setDuoTeamRed(
                    duoTeam );
            }
            else
            {
                fightMap.get( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).setTeamIdRed(
                    LONG_DEFAULT );
                fightMap.get( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).setDuoTeamRed(
                    null );
            }
        }

        if ( FIGHTER_RED == redBlue )
        {
            otherTeamId =
                fightMap.get( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).getTeamIdRed();
            otherDuoTeam =
                fightMap.get( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).getDuoTeamRed();

            fightMap.get( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).resetForKoList();
            fightMap.get( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).setTeamIdRed(
                otherTeamId );
            fightMap.get( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).setDuoTeamRed(
                otherDuoTeam );

            if ( duoTeam != null )
            {
                fightMap.get( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).setTeamIdBlue(
                    duoTeam.getId() );
                fightMap.get( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).setDuoTeamBlue(
                    duoTeam );
            }
            else
            {
                fightMap.get( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).setTeamIdBlue(
                    LONG_DEFAULT );
                fightMap.get( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).setDuoTeamBlue(
                    null );
            }
        }

        fightMap.get( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).setModificationId( LONG_1 );
        fightMap.get( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() ).setModificationDate(
            new Timestamp( System.currentTimeMillis() ) );

        return fightMap.get( SOLACE_NEXT_FIGHT_MAP.get( numberOfFight ).getNumberInSolaceRound() );
    }

    
    
    private void handle3rdPlaceFights( DuoKoClass fc, List<DuoFight> changeFights, DuoFightDao fightDao ) throws JJWDataLayerException
    {
        boolean stopMethod =false;
        
        //when fight for 5th place has no winner, so we must ensure that no blue participant for 3rd place fight exists
        if(LONG_MIN == fc.getFightListLooserMapPoolA().get( 1 ).getWinnerId() && LONG_MIN != fc.getFightListLooserMapPoolA().get( 0 ).getTeamIdBlue()){
            DuoFight fight = fc.getFightListLooserMapPoolA().get( 0 );
            long otherTeamId = fight.getTeamIdRed();
            DuoTeam otherTeam = fight.getDuoTeamRed();
            fight.resetForKoList();
            fight.setDuoTeamRed( otherTeam );
            fight.setTeamIdRed( otherTeamId );
            fight.setDuoTeamBlue( null );           
            fight.setTeamIdBlue( LONG_EMPTY );
           
            fight.setModificationId( LONG_1 );
            fight.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
            changeFights.add( fight );
            stopMethod=true;
        }
        
      //when fight for 5th place has no winner, so we must ensure that no blue participant for 3rd place fight exists
        if(LONG_MIN == fc.getFightListLooserMapPoolB().get( 1 ).getWinnerId() && LONG_MIN != fc.getFightListLooserMapPoolB().get( 0 ).getTeamIdBlue()){
            DuoFight fight = fc.getFightListLooserMapPoolB().get( 0 );
            long otherTeamId = fight.getTeamIdRed();
            DuoTeam otherTeam = fight.getDuoTeamRed();
            fight.resetForKoList();
            fight.setDuoTeamRed( otherTeam );
            fight.setTeamIdRed( otherTeamId );
            fight.setDuoTeamBlue( null );           
            fight.setTeamIdBlue( LONG_EMPTY );
           
            fight.setModificationId( LONG_1 );
            fight.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
            changeFights.add( fight );
            stopMethod=true;
        }
        
        
        
        // only perform this method, when we have red or blue opponents for 3rd place fights
        if (stopMethod 
            || LONG_MIN == fc.getFightListLooserMapPoolA().get( 0 ).getTeamIdRed()
            || LONG_MIN == fc.getFightListLooserMapPoolB().get(0 ).getTeamIdRed()
            || LONG_MIN == fc.getFightListLooserMapPoolA().get( 0 ).getTeamIdBlue()
            || LONG_MIN == fc.getFightListLooserMapPoolB().get( 0 ).getTeamIdBlue())
            return;

        
        long blueTeamSolaceRoundA = LONG_EMPTY, blueTeamSolaceRoundB = LONG_EMPTY;
        DuoTeam redTeamA=fc.getFightListLooserMapPoolA().get( 0 ).getDuoTeamRed();
        DuoTeam redTeamB=fc.getFightListLooserMapPoolB().get( 0 ).getDuoTeamRed();
        long redTeamSolaceRoundA = fc.getFightListLooserMapPoolA().get( 0 ).getTeamIdRed();
        long redTeamSolaceRoundB = fc.getFightListLooserMapPoolB().get( 0 ).getTeamIdRed();
        DuoFight solaceRoundA5thPlaceFight = fc.getFightListLooserMapPoolA().get( Integer.valueOf( 1 ) );
        DuoFight solaceRoundB5thPlaceFight = fc.getFightListLooserMapPoolB().get( Integer.valueOf( 1 ) );

        if ( ( solaceRoundA5thPlaceFight.getTeamIdRed() == solaceRoundA5thPlaceFight.getWinnerId().longValue() && solaceRoundA5thPlaceFight.getTeamIdRed() != LONG_MIN )
            || ( solaceRoundA5thPlaceFight.getWinnerId().longValue() == LONG_MIN
                && solaceRoundA5thPlaceFight.getTeamIdBlue() == LONG_DEFAULT && solaceRoundA5thPlaceFight.getTeamIdRed() > LONG_DEFAULT ) )
        {
            blueTeamSolaceRoundA = solaceRoundA5thPlaceFight.getTeamIdRed();
        }

        if ( ( solaceRoundA5thPlaceFight.getTeamIdBlue() == solaceRoundA5thPlaceFight.getWinnerId().longValue() && solaceRoundA5thPlaceFight.getTeamIdBlue() != LONG_MIN )
            || ( solaceRoundA5thPlaceFight.getWinnerId().longValue() == LONG_MIN
                && solaceRoundA5thPlaceFight.getTeamIdBlue() > LONG_DEFAULT && solaceRoundA5thPlaceFight.getTeamIdRed() == LONG_DEFAULT ) )
        {
            blueTeamSolaceRoundA = solaceRoundA5thPlaceFight.getTeamIdBlue();
        }

        if ( ( solaceRoundB5thPlaceFight.getTeamIdRed() == solaceRoundB5thPlaceFight.getWinnerId().longValue() && solaceRoundB5thPlaceFight.getTeamIdRed() != LONG_MIN )
            || ( solaceRoundB5thPlaceFight.getWinnerId().longValue() == LONG_MIN
                && solaceRoundB5thPlaceFight.getTeamIdBlue() == LONG_DEFAULT && solaceRoundB5thPlaceFight.getTeamIdRed() > LONG_DEFAULT ) )
        {
            blueTeamSolaceRoundB = solaceRoundB5thPlaceFight.getTeamIdRed();
        }

        if ( ( solaceRoundB5thPlaceFight.getTeamIdBlue() == solaceRoundB5thPlaceFight.getWinnerId().longValue() && solaceRoundB5thPlaceFight.getTeamIdBlue() != LONG_MIN )
            || ( solaceRoundB5thPlaceFight.getWinnerId().longValue() == LONG_MIN
                && solaceRoundB5thPlaceFight.getTeamIdBlue() > LONG_DEFAULT && solaceRoundB5thPlaceFight.getTeamIdRed() == LONG_DEFAULT ) )
        {
            blueTeamSolaceRoundB = solaceRoundB5thPlaceFight.getTeamIdBlue();
        }

        if ( LONG_EMPTY == redTeamSolaceRoundA || LONG_EMPTY == blueTeamSolaceRoundA || LONG_EMPTY == redTeamSolaceRoundB
            || LONG_EMPTY == blueTeamSolaceRoundB )
        {

            handle3rdPlaceFightsWithFreeFight( fc, changeFights, fightDao, blueTeamSolaceRoundA,
                                               blueTeamSolaceRoundB, redTeamSolaceRoundA, redTeamSolaceRoundB,redTeamA,redTeamB );
        }
        else
        {
            handle3rdPlaceFightsWith4Participans( fc, changeFights, fightDao, blueTeamSolaceRoundA,
                                                  blueTeamSolaceRoundB, redTeamSolaceRoundA,
                                                  redTeamSolaceRoundB ,redTeamA,redTeamB);
        }

        // test, if there is a free fight for the 3rd place and set the winner
        if ( fc.getFightListLooserMapPoolA().get( 0 ).getTeamIdBlue() == LONG_DEFAULT
            && fc.getFightListLooserMapPoolA().get( 0 ).getTeamIdRed() > LONG_DEFAULT
            && fc.getFightListLooserMapPoolA().get( 0 ).getWinnerId().longValue() == LONG_MIN )
        {

            fc.getFightListLooserMapPoolA().get( 0 ).setWinnerId( fc.getFightListLooserMapPoolA().get( 0 ).getTeamIdRed() );
            changeFights.add( fc.getFightListLooserMapPoolA().get( 0 ) );
        }
        if ( fc.getFightListLooserMapPoolA().get( 0 ).getTeamIdRed() == LONG_DEFAULT
            && fc.getFightListLooserMapPoolA().get( 0 ).getTeamIdBlue() > LONG_DEFAULT
            && fc.getFightListLooserMapPoolA().get( 0 ).getWinnerId().longValue() == LONG_MIN )
        {

            fc.getFightListLooserMapPoolA().get( 0 ).setWinnerId( fc.getFightListLooserMapPoolA().get( 0 ).getTeamIdBlue() );
            changeFights.add( fc.getFightListLooserMapPoolA().get( 0 ) );
        }

        // test, if there is a free fight for the 3rd place and set the winner
        if ( fc.getFightListLooserMapPoolB().get( 0 ).getTeamIdBlue() == LONG_DEFAULT
            && fc.getFightListLooserMapPoolB().get( 0 ).getTeamIdRed() > LONG_DEFAULT
            && fc.getFightListLooserMapPoolB().get( 0 ).getWinnerId().longValue() == LONG_MIN )
        {

            fc.getFightListLooserMapPoolB().get( 0 ).setWinnerId( fc.getFightListLooserMapPoolB().get( 0 ).getTeamIdRed() );
            changeFights.add( fc.getFightListLooserMapPoolA().get( 0 ) );
        }
        if ( fc.getFightListLooserMapPoolB().get( 0 ).getTeamIdRed() == LONG_DEFAULT
            && fc.getFightListLooserMapPoolB().get( 0 ).getTeamIdBlue() > LONG_DEFAULT
            && fc.getFightListLooserMapPoolB().get( 0 ).getWinnerId().longValue() == LONG_MIN )
        {

            fc.getFightListLooserMapPoolB().get( 0 ).setWinnerId( fc.getFightListLooserMapPoolB().get( 0 ).getTeamIdBlue() );
            changeFights.add( fc.getFightListLooserMapPoolB().get( 0 ) );
        }
    }

    private void handle3rdPlaceFightsWithFreeFight( DuoKoClass fc, List<DuoFight> changeFights, DuoFightDao fightDao,
                                                    long blueTeamSolaceRoundA, long blueTeamSolaceRoundB,
                                                    long redTeamSolaceRoundA, long redTeamSolaceRoundB, DuoTeam redTeamA , DuoTeam redTeamB  )
        throws JJWDataLayerException
    {
        if ( LONG_EMPTY != redTeamSolaceRoundA && LONG_EMPTY != blueTeamSolaceRoundA )
        {
            if ( fightDao.isDoneFightRegardlessRedBlue( fc.getId(), redTeamSolaceRoundA,
                                                        blueTeamSolaceRoundA ) )
            {// we need to change --> now test if we are allowed to change
                if ( ( redTeamSolaceRoundB != LONG_EMPTY && !fightDao.isDoneFightRegardlessRedBlue( fc.getId(),
                                                                                                 redTeamSolaceRoundB,
                                                                                                 blueTeamSolaceRoundA ) )
                    || ( blueTeamSolaceRoundB != LONG_EMPTY && !fightDao.isDoneFightRegardlessRedBlue( fc.getId(),
                                                                                                    redTeamSolaceRoundA,
                                                                                                    blueTeamSolaceRoundB ) ) )
                {
                    // no change
                }
                else
                {   // change
                    fc.setThirdPlaceFightsChanged( true );
                    if ( ( redTeamSolaceRoundB == LONG_EMPTY && fc.getFightListLooserMapPoolA().get( 0 ).getTeamIdRed() > LONG_DEFAULT )

                        || ( redTeamSolaceRoundB != LONG_EMPTY && fc.getFightListLooserMapPoolA().get( 0 ).getTeamIdRed() != redTeamSolaceRoundB ) )
                    {
                        DuoFight fight = fc.getFightListLooserMapPoolA().get( 0 );
                        long otherTeamId = fight.getTeamIdBlue();
                        DuoTeam otherTeam = fight.getDuoTeamBlue();
                        fight.resetForKoList();
                        fight.setDuoTeamBlue( otherTeam );
                        fight.setTeamIdBlue( otherTeamId );
                        fight.setDuoTeamRed( redTeamB );
                        if ( redTeamSolaceRoundB != LONG_EMPTY )
                            fight.setTeamIdRed( redTeamSolaceRoundB );
                        else
                            fight.setTeamIdRed( LONG_DEFAULT );
                        fight.setModificationId( LONG_1 );
                        fight.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
                        changeFights.add( fight );
                    }

                    if ( ( redTeamSolaceRoundA == LONG_EMPTY && fc.getFightListLooserMapPoolB().get( 0 ).getTeamIdRed() > LONG_DEFAULT )
                        || ( redTeamSolaceRoundA != LONG_EMPTY && fc.getFightListLooserMapPoolB().get( 0 ).getTeamIdRed() != redTeamSolaceRoundA ) )
                    {
                        DuoFight fight = fc.getFightListLooserMapPoolB().get( 0 );
                        long otherTeamId = fight.getTeamIdBlue();
                        DuoTeam otherTeam = fight.getDuoTeamBlue();
                        fight.resetForKoList();
                        fight.setDuoTeamBlue( otherTeam );
                        fight.setTeamIdBlue( otherTeamId );
                        fight.setDuoTeamRed( redTeamA );
                        fight.setTeamIdRed( redTeamSolaceRoundA );

                        fight.setModificationId( LONG_1 );
                        fight.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
                        changeFights.add( fight );
                    }
                }
            }               
        }
        else
        {
            if ( LONG_EMPTY != redTeamSolaceRoundB && LONG_EMPTY != blueTeamSolaceRoundB )
            {
                if ( fightDao.isDoneFightRegardlessRedBlue(fc.getId(), redTeamSolaceRoundB,
                                                           blueTeamSolaceRoundB )  )
                {// we need to change --> now test if we are allowed to change
                    if ( ( redTeamSolaceRoundA != LONG_EMPTY && !fightDao.isDoneFightRegardlessRedBlue( fc.getId(),
                                                                                                     redTeamSolaceRoundA,
                                                                                                     blueTeamSolaceRoundB) )
                        || ( blueTeamSolaceRoundA != LONG_EMPTY && !fightDao.isDoneFightRegardlessRedBlue( fc.getId(),
                                                                                                        redTeamSolaceRoundB,
                                                                                                        blueTeamSolaceRoundA ) ) )
                    {
                        // no change
                    }
                    else{
                        //change
                        fc.setThirdPlaceFightsChanged( true );
                        if ( ( redTeamSolaceRoundA == LONG_EMPTY && fc.getFightListLooserMapPoolB().get( 0 ).getTeamIdRed() > LONG_DEFAULT )

                            || ( redTeamSolaceRoundA != LONG_EMPTY && fc.getFightListLooserMapPoolB().get( 0 ).getTeamIdRed() != redTeamSolaceRoundA ) )
                        {
                            DuoFight fight = fc.getFightListLooserMapPoolB().get( 0 );
                            long otherTeamId = fight.getTeamIdBlue();
                            DuoTeam otherTeam = fight.getDuoTeamBlue();
                            fight.resetForKoList();
                            fight.setDuoTeamBlue( otherTeam );
                            fight.setTeamIdBlue( otherTeamId );
                            fight.setDuoTeamRed( redTeamA );
                            if ( redTeamSolaceRoundA != LONG_EMPTY )
                                fight.setTeamIdRed( redTeamSolaceRoundA);
                            else
                                fight.setTeamIdRed( LONG_DEFAULT );
                            fight.setModificationId( LONG_1 );
                            fight.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
                            changeFights.add( fight );
                        }

                        if ( ( redTeamSolaceRoundB == LONG_EMPTY && fc.getFightListLooserMapPoolA().get( 0 ).getTeamIdRed() > LONG_DEFAULT )
                            || ( redTeamSolaceRoundB != LONG_EMPTY && fc.getFightListLooserMapPoolA().get( 0 ).getTeamIdRed() != redTeamSolaceRoundB ) )
                        {
                            DuoFight fight = fc.getFightListLooserMapPoolA().get( 0 );
                            long otherTeamId = fight.getTeamIdBlue();
                            DuoTeam otherTeam = fight.getDuoTeamBlue();
                            fight.resetForKoList();
                            fight.setDuoTeamBlue( otherTeam );
                            fight.setTeamIdBlue( otherTeamId );
                            fight.setDuoTeamRed( redTeamB );
                            fight.setTeamIdRed( redTeamSolaceRoundB );

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

    private void handle3rdPlaceFightsWith4Participans( DuoKoClass fc, List<DuoFight> changeFights, DuoFightDao fightDao,
                                                  long blueTeamSolaceRoundA, long blueTeamSolaceRoundB,
                                                  long redTeamSolaceRoundA, long redTeamSolaceRoundB, DuoTeam redTeamA , DuoTeam redTeamB  )
        throws JJWDataLayerException
    {
        // 4 fighter , no DQs
        // 1st: test if we need to change
        // 2nd: are we allowed to change, to avoid that after change is a conflict

        if ( fightDao.isDoneFightRegardlessRedBlue( fc.getId(), redTeamSolaceRoundA,
                                                    blueTeamSolaceRoundA )
            || fightDao.isDoneFightRegardlessRedBlue( fc.getId(), redTeamSolaceRoundB,
                                                      blueTeamSolaceRoundB ) )
        {
            // we need to change
            if ( fightDao.isDoneFightRegardlessRedBlue( fc.getId(), redTeamSolaceRoundA,
                                                        blueTeamSolaceRoundB )
                || fightDao.isDoneFightRegardlessRedBlue( fc.getId(), redTeamSolaceRoundB,
                                                          blueTeamSolaceRoundA) )
            {
                //we are not allowed to change
                if ( fc.getFightListLooserMapPoolA().get( 0 ).getTeamIdRed() != redTeamSolaceRoundA )
                {

                    DuoFight fight = fc.getFightListLooserMapPoolA().get( 0 );
                    long otherTeamId = fight.getTeamIdBlue();
                    if ( fc.getOriginal3rdPlaceFightA().getTeamIdRed() == redTeamSolaceRoundA
                        && fc.getOriginal3rdPlaceFightA().getTeamIdBlue() == otherTeamId )
                    {
                        // test, if we use the fight from database or reset it
                        DuoFightMapper.mapFightToDB( fc.getOriginal3rdPlaceFightA(), fight, false );
                        fc.getFightListLooserMapPoolA().put( 0, fc.getOriginal3rdPlaceFightA() );
                        changeFights = removeChangedFightFromList( fight.getId(), changeFights );
                    }
                    else
                    {
                        DuoTeam otherTeam = fight.getDuoTeamBlue();
                        fight.resetForKoList();
                        fight.setDuoTeamBlue( otherTeam );
                        fight.setTeamIdBlue( otherTeamId );
                        fight.setDuoTeamRed( redTeamA );
                        fight.setTeamIdRed( redTeamSolaceRoundA );

                        fight.setModificationId( LONG_1 );
                        fight.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
                        changeFights.add( fight );
                    }
                }
                
                if(fc.getFightListLooserMapPoolB().get( 0 ).getTeamIdRed() != redTeamSolaceRoundB){                        
                    
                    DuoFight fight = fc.getFightListLooserMapPoolB().get( 0 );
                    long otherTeamId =fight.getTeamIdBlue();
                    if ( fc.getOriginal3rdPlaceFightB().getTeamIdRed() == redTeamSolaceRoundB
                        && fc.getOriginal3rdPlaceFightB().getTeamIdBlue() == otherTeamId )
                    {
                        // test, if we use the fight from database or reset it
                        DuoFightMapper.mapFightToDB( fc.getOriginal3rdPlaceFightB(), fight, false );
                        fc.getFightListLooserMapPoolB().put( 0, fc.getOriginal3rdPlaceFightB() );
                        changeFights = removeChangedFightFromList( fight.getId(), changeFights );
                    }
                    else
                    {
                        DuoTeam otherTeam = fight.getDuoTeamBlue();
                        fight.resetForKoList();
                        fight.setDuoTeamBlue( otherTeam );
                        fight.setTeamIdBlue( otherTeamId );
                        fight.setDuoTeamRed( redTeamB );
                        fight.setTeamIdRed( redTeamSolaceRoundB );

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
                if ( fc.getFightListLooserMapPoolA().get( 0 ).getTeamIdRed() != redTeamSolaceRoundB )
                {

                    DuoFight fight = fc.getFightListLooserMapPoolA().get( 0 );
                    long otherTeamId = fight.getTeamIdBlue();
                    if ( fc.getOriginal3rdPlaceFightA().getTeamIdRed() == redTeamSolaceRoundB
                        && fc.getOriginal3rdPlaceFightA().getTeamIdBlue() == otherTeamId )
                    {
                        // test, if we use the fight from database or reset it
                        DuoFightMapper.mapFightToDB( fc.getOriginal3rdPlaceFightA(), fight, false );
                        fc.getFightListLooserMapPoolA().put( 0, fc.getOriginal3rdPlaceFightA() );
                        changeFights = removeChangedFightFromList( fight.getId(), changeFights );
                    }
                    else
                    {
                        DuoTeam otherTeam = fight.getDuoTeamBlue();
                        fight.resetForKoList();
                        fight.setDuoTeamBlue( otherTeam );
                        fight.setTeamIdBlue( otherTeamId );
                        fight.setDuoTeamRed( redTeamB );
                        fight.setTeamIdRed( redTeamSolaceRoundB );

                        fight.setModificationId( LONG_1 );
                        fight.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
                        changeFights.add( fight );
                    }
                }

                if ( fc.getFightListLooserMapPoolB().get( 0 ).getTeamIdRed() != redTeamSolaceRoundA )
                {

                    DuoFight fight = fc.getFightListLooserMapPoolB().get( 0 );
                    long otherTeamId = fight.getTeamIdBlue();
                    if ( fc.getOriginal3rdPlaceFightB().getTeamIdRed() == redTeamSolaceRoundA
                        && fc.getOriginal3rdPlaceFightB().getTeamIdBlue() == otherTeamId )
                    {
                        // test, if we use the fight from database or reset it
                        DuoFightMapper.mapFightToDB( fc.getOriginal3rdPlaceFightB(), fight, false );
                        fc.getFightListLooserMapPoolB().put( 0, fc.getOriginal3rdPlaceFightB() );
                        changeFights = removeChangedFightFromList( fight.getId(), changeFights );
                    }
                    else
                    {
                        DuoTeam otherTeam = fight.getDuoTeamBlue();
                        fight.resetForKoList();
                        fight.setDuoTeamBlue( otherTeam );
                        fight.setTeamIdBlue( otherTeamId );
                        fight.setDuoTeamRed( redTeamA );
                        fight.setTeamIdRed( redTeamSolaceRoundA );

                        fight.setModificationId( LONG_1 );
                        fight.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
                        changeFights.add( fight );
                    }
                }
            }
        }
    }

    private List<DuoFight> removeChangedFightFromList( long fightId, List<DuoFight> changeFights )
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