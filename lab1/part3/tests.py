import unittest

from square import Square


class TestFillingSquare(unittest.TestCase):
    ''' Class tests different ways to add objects to the square. 
    
    This is needed to check if we have mistake on the construction phase while
    running some advanced test. 
    Every test have also _again() pair to check what is going to happen after
    the second run of the same method.
    '''

    def test_bringing_crowd(self):
        square = Square()
        crowd = square.bring_crowd()
        self.assertIsNotNone(crowd)

    def test_bringing_crowd_again(self):
        square = Square()
        square.bring_crowd()
        self.assertRaises(RuntimeError, square.bring_crowd)

    def test_constructing_platform(self):
        square = Square()
        platform = square.construct_platform()
        self.assertIsNotNone(platform)

    def test_constructing_platform_again(self):
        square = Square()
        platform = square.construct_platform()
        self.assertRaises(RuntimeError, square.construct_platform)

    def test_inviting_speaker_without_platform(self):
        square = Square()
        self.assertRaises(ValueError, square.invite_speaker)

    def test_inviting_speaker_on_platform(self):
        square = Square()
        square.construct_platform()
        self.assertIsNotNone(square.invite_speaker())

    def test_inviting_speaker_again(self):
        square = Square()
        square.construct_platform()
        square.invite_speaker()
        self.assertRaises(ValueError, square.invite_speaker)


class TestSquareWithoutInizializaiton(unittest.TestCase):
    ''' This suite checks consequnces of usage of Square without inizializaiton
    
    To separate this problems from the main suite which is TestSquare
    '''

    def test_speaker_talk_without_invite(self):
        square = Square()
        self.assertRaises(ValueError, square.speaker_talk)

    def test_noise_without_crowd(self):
        square = Square()
        self.assertRaises(ValueError, square.make_noise)


class TestSquare(unittest.TestCase):
    ''' Tests how different objects on the square interact with each other

    In this case we assume, that user knows how to fill the square
    since it was alsready tested in TestFillingSquare.
    Sentence above means that return values of 'adding' functions won't be 
    tested in this case, only integration of different attributes of Square
    '''

    def test_default_sequence(self):
        square = Square()

        square.construct_platform()
        square.bring_crowd()
        square.invite_speaker()

        square.speaker_talk()
        square.make_noise()
        self.assertNotEqual(square.arthur.state, "flying")
        square.make_noise()

        # at this point Arthur should be flying
        self.assertEqual(square.arthur.state, "flying")

    def test_noise_on_unprepared_square(self):
        ''' People make noise in the square, nothing else is set

        This test aims to check if Arthur is going to fly without platform
        and speaker being on their places (he should not)
        '''
        square = Square()

        crowd = square.bring_crowd()
        
        square.make_noise()
        square.make_noise()

        self.assertNotEqual(square.arthur.state, "flying")

    def test_noise_on_square_without_speaker(self):
        ''' People make noise in the square, nothing else is set

        Modification of the previous tests which aims to check if Arthur will
        be flying in the absence of speaker.
        '''
        square = Square()

        crowd = square.bring_crowd()
        
        square.make_noise()
        square.make_noise()

        self.assertNotEqual(square.arthur.state, "flying")

    def test_crowd_invites_speaker(self):
        ''' Just another way to make Arthur fly

        Crowd comes makes some noise, platform is getting build, they invite
        speaker and one more noise -- Arthur is flying
        '''
        square = Square()
        square.bring_crowd()
        square.make_noise()
        square.construct_platform()
        square.invite_speaker()
        square.speaker_talk()
        self.assertNotEqual(square.arthur.state, "flying")
        square.make_noise()
        self.assertEqual(square.arthur.state, "flying")


if __name__ == '__main__':
    unittest.main()
